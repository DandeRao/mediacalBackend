package com.patient.services;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.*;

import com.patient.models.responses.*;
import com.patient.models.responses.Cancer;
import com.patient.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PatientService {

    @Autowired
    private RegimenDetailRepository regimenDetailRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CancerRepository cancerRepository;

    @Autowired
    private CancerRegimenLinkRepository cancerRegimenLinkRepository;

    @Autowired
    private LevelTypeRepository levelTypeRepository;

    @Autowired
    private RegimenLevelLinkRepository regimenLevelLinkRepository;

    @Autowired
    private CTGSGroupRepository ctgsGroupRepository;

    Map<Integer, String> levelTypeMap = new HashMap<>();

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public long deletePatient(int id) {
        Patient patient = patientRepository.findOne(id);
        patientRepository.delete(patient);
        return id;
    }

    public Patient addOrUpdatePatient(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        Patient patient1 = objectMapper.readValue(payLoad, Patient.class);
        Patient patient = Patient.builder()
                .title(patient1.getTitle())
                .id(null != patient1.getId() ? patient1.getId() : patientRepository.getMaxId() + 1)
                .build();
        return patientRepository.save(patient);
    }

    public AllData getAllData() {
        AllData allData = new AllData();
        fillLevelTypes();
        List<Patient> patients = patientRepository.findAll();

        for (Patient patient : patients) {
            PatientType patientType = new PatientType();
            patientType.setType(patient.getTitle());
            // for each patient get cancers
            List<com.patient.models.Cancer> cancersFromRepoList = cancerRepository.getCancersByPatientId(patient.getId());
            List<Cancer> cancersToPutInPatientResponse = new ArrayList<>();

            for (com.patient.models.Cancer cancer : cancersFromRepoList) {
                Cancer responseCancer = new com.patient.models.responses.Cancer();
                responseCancer.setId(cancer.getId());
				responseCancer.setTitle(cancer.getTitle());
				populateRegimenForCancer(responseCancer);
                populateSubCancersForCancer(responseCancer);

                cancersToPutInPatientResponse.add(responseCancer);
            }
            patientType.setCancers(cancersToPutInPatientResponse);
            allData.getPatients().add(patientType);
        }

        for (RegimenDetail regimenDetail : regimenDetailRepository.findAll()) {
            Regimen regimen = new Regimen();
            regimen.setId(regimenDetail.getId());
            regimen.setSchedule(regimenDetail.getSchedule());
            regimen.setRegimenLevels(regimenDetail.getRegimenLevels());
            regimen.setEmetogenicPotential(regimenDetail.getEmetogenicPotential());
            regimen.setDosageModifications(regimenDetail.getDosageModifications());
            regimen.setDrugs(regimenDetail.getBrands());
            regimen.setRegimenLevels(regimenDetail.getRegimenLevels());
            regimen.setName(regimenDetail.getDispName());
            regimen.setReferences(regimenDetail.getReferences());
            allData.getRegimen().put(String.valueOf(regimen.getId()), regimen);
        }

        allData.setDrugs(drugRepository.findAll(new Sort(Sort.Direction.ASC, "genericName")));
        Map<Integer, CtgsGroup> ctgsGroupMap = new LinkedHashMap<>();

        for (CtgsGroup ctgsGroup : ctgsGroupRepository.findAll()) {
            ctgsGroupMap.put(ctgsGroup.getId(),ctgsGroup);
        }

        allData.setCtgsGroups(ctgsGroupMap);

        Map<Integer, LevelType> levelTypeMap = new LinkedHashMap<>();



        for (LevelType levelType : levelTypeRepository.findAll()) {
           levelTypeMap.put(levelType.getId(),levelType);
        }

        allData.setRegimenLevel(levelTypeMap);

        return allData;
    }

    private Cancer populateSubCancersForCancer(Cancer cancer) {
        List<com.patient.models.Cancer> subCancersForCancer = cancerRepository.getCancersByParentId(cancer.getId());

		List<Cancer> subCancers = new ArrayList<>();
        if (null != subCancersForCancer && subCancersForCancer.size() > 0) {
            // this block is for building sub cancers only
            for (com.patient.models.Cancer subCancerFromDB : subCancersForCancer) {
                Cancer responseCancer = new Cancer();
                responseCancer.setTitle(subCancerFromDB.getTitle());
                responseCancer.setId(subCancerFromDB.getId());

				populateRegimenForCancer(responseCancer);

                populateSubCancersForCancer(responseCancer);
                subCancers.add(responseCancer);
            }
        }

        // need to build up the cancer here
		cancer.setSubCancers(subCancers);
        return cancer;
    }

    private void populateRegimenForCancer(Cancer cancer) {
		// setting regimen under cancer.regimenLevel
		List<CancerRegimenLink> cancerRegimenLinks = cancerRegimenLinkRepository.getCancerRegimenLinkByCancerId(cancer.getId());
		if (null != cancerRegimenLinks && cancerRegimenLinks.size() > 0) {
			Map<Integer, RegimenLevel> levelTitleToRegimenLevelMap = new HashMap<>();
			for (CancerRegimenLink cancerRegimenLink : cancerRegimenLinks) {
				List<RegimenLevelLink> regimenLevelLinks = regimenLevelLinkRepository.getLevelTypeByRegimenId(cancerRegimenLink.getRegimenId());

				// this block for regimen with level type links.
				if (null != regimenLevelLinks && regimenLevelLinks.size() > 0) {
					for (RegimenLevelLink regimenLevelLink : regimenLevelLinks) {
						Integer levelTitle = regimenLevelLink.getLevelId();

						RegimenLevel regimenLevel = levelTitleToRegimenLevelMap.get(levelTitle);

						if (null == regimenLevel) {
							regimenLevel = new RegimenLevel();
						}

						regimenLevel.setRegimenLevelId(levelTitle);
						regimenLevel.getRegimenIds().add(cancerRegimenLink.getRegimenId());

						levelTitleToRegimenLevelMap.put(levelTitle, regimenLevel);
					}
				} else {
                    RegimenLevel regimenLevel = levelTitleToRegimenLevelMap.get(-1);

                    if (null == regimenLevel) {
                        regimenLevel = new RegimenLevel();
                    }

                    regimenLevel.setRegimenLevelId(-1);
                    regimenLevel.getRegimenIds().add(cancerRegimenLink.getRegimenId());
                    levelTitleToRegimenLevelMap.put(-1, regimenLevel);

                }
			}
			cancer.getRegimenByLevelList().addAll(levelTitleToRegimenLevelMap.values());
		}
	}
    private void fillLevelTypes() {
        // this block to get the level type, need to run only once per function call.
        List<LevelType> levelTypes = levelTypeRepository.findAll();
        levelTypeMap = new HashMap<>();

        for (LevelType lt : levelTypes) {
            levelTypeMap.put(lt.getId(), lt.getLevel());
        }
    }

}
