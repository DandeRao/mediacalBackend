package com.patient.services;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.CancerResponse;
import com.patient.models.Patient;
import com.patient.models.RegimenDetail;
import com.patient.models.responses.AllData;
import com.patient.models.responses.PatientType;
import com.patient.models.responses.Regimen;
import com.patient.repos.DrugRepository;
import com.patient.repos.RegimenDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.repos.PatientRepository;

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
	private CancerService cancerService;


	public List<Patient> getAllPatients(){
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
                .id( null != patient1.getId() ? patient1.getId() : patientRepository.getMaxId()+1)
                .build();
		return patientRepository.save(patient);
	}

	public AllData getAllData() {
		AllData allData = new AllData();
		List<Patient> patients = patientRepository.findAll();

		for(Patient patient: patients) {
			PatientType patientType = new PatientType();
			patientType.setType(patient.getTitle());

			patientType.getCancers().addAll(cancerService.getCancerResponseByPatientId(patient.getId()));

			allData.getPatients().add(patientType);
		}

		for (RegimenDetail regimenDetail: regimenDetailRepository.findAll()) {
			Regimen regimen = new Regimen();
			regimen.setId(regimenDetail.getId());
			regimen.setSchedule(regimenDetail.getSchedule());
			regimen.setRegimenLevels(regimenDetail.getRegimenLevels());
			regimen.setEmetogenicPotential(regimenDetail.getEmetogenicPotential());
			regimen.setDosageModifications(regimenDetail.getDosageModifications());
			regimen.setDrugs(regimenDetail.getBrands());
			regimen.setName(regimenDetail.getName());
			regimen.setReferences(regimenDetail.getReferences());

			allData.getRegimen().add(regimen);
		}

		allData.setDrugs(drugRepository.findAll());

		return allData;
	}
}
