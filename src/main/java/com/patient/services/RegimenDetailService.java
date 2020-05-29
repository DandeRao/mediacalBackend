package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.*;
import com.patient.repos.CancerRepository;
import com.patient.repos.LevelTypeRepository;
import com.patient.repos.PatientRepository;
import com.patient.repos.RegimenDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegimenDetailService {

    @Autowired
    private RegimenDetailRepository regimenDetailRepository;

    @Autowired
    private CancerRepository cancerRepository;

    @Autowired
    private CancerService cancerService;

    @Autowired
    private ObjectMapper objectMapper;

  @Autowired
  private LevelTypeRepository levelTypeRepository;

  @Autowired
  private PatientRepository patientRepository;

    public List<RegimenDetail> getAllRegimenDetails(){
        return regimenDetailRepository.findAll();
    }

    public RegimenDetail getRegimenDetailId(int id){
        return regimenDetailRepository.findOne((long) id);
    }

    public int deleteRegimenDetail(int id) {
        regimenDetailRepository.delteRegimenWithId(id);
        return id;
    }

    public RegimenDetail addOrUpdateRegimenDetail(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        RegimenDetail regimenDetail1 = objectMapper.readValue(payLoad, RegimenDetail.class);

        RegimenDetail regimenDetail = RegimenDetail.builder()
                .id(getRegimenId(regimenDetail1))
                .brandNames(regimenDetail1.getBrandNames())
                .dispName(regimenDetail1.getDispName())
                .reference(regimenDetail1.getReference())
                .name(regimenDetail1.getName())
                .emetogenicPotential(regimenDetail1.getEmetogenicPotential())
                .dispName(regimenDetail1.getDispName())
                .dosageModifications(regimenDetail1.getDosageModifications())
                .schedule(regimenDetail1.getSchedule())
                .regimenType(regimenDetail1.getRegimenType())
                .regimenLevels(regimenDetail1.getRegimenLevels())
                .brands(regimenDetail1.getBrands())
                .build();

      saveRegimenInCancer(regimenDetail);

      return regimenDetailRepository.save(regimenDetail);
    }

    public LevelType addLevel(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        LevelType level = objectMapper.readValue(payLoad, LevelType.class);

        LevelType existingLevel = levelTypeRepository.findLevelTypeByLevelName(level.getLevel());

        if(null == existingLevel) {
          LevelType levelDetail = LevelType.builder()
                  .level(level.getLevel())
                  .build();

          return levelTypeRepository.save(levelDetail);
        }

        return level;
    }

  public String deleteLevel(String payLoad) throws JsonParseException, JsonMappingException, IOException {

      LevelType levelType = levelTypeRepository.findLevelTypeByLevelName(payLoad);
      levelTypeRepository.delete(levelType);

    return payLoad;
  }

    public RegimenDetail updateRegimenDetail(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        RegimenDetail regimenDetail1 = objectMapper.readValue(payLoad, RegimenDetail.class);
        RegimenDetail regimenDetail = regimenDetailRepository.fingRegimenById(regimenDetail1.getId());

        if(regimenDetail.getBrandNames() != regimenDetail1.getBrandNames()
           | regimenDetail.getDispName() != regimenDetail1.getDispName()
           |  regimenDetail.getDosageModifications() != regimenDetail1.getDosageModifications()
           | regimenDetail.getEmetogenicPotential() != regimenDetail.getDosageModifications()
           | regimenDetail.getSchedule() != regimenDetail1.getSchedule()
                | regimenDetail.getReference() != regimenDetail1.getReference()){

            regimenDetail.setBrandNames(regimenDetail1.getBrandNames());
            regimenDetail.setDispName(regimenDetail1.getDispName());
            regimenDetail.setDosageModifications(regimenDetail1.getDosageModifications());
            regimenDetail.setReference(regimenDetail1.getReference());
            regimenDetail.setEmetogenicPotential(regimenDetail1.getEmetogenicPotential());
            regimenDetail.setSchedule(regimenDetail1.getSchedule());
            regimenDetail.setRegimenType(regimenDetail1.getRegimenType());
            regimenDetail.setRegimenLevels(regimenDetail.getRegimenLevels());
            regimenDetail.setBrands(regimenDetail.getBrands());
            saveRegimenInCancer(regimenDetail);
            return regimenDetailRepository.save(regimenDetail);
        }

        return regimenDetail;
    }

    private void  saveRegimenInCancer(RegimenDetail regimenDetail)
    {
//      if (null != regimenDetail.getSubCancerTypeId3())
//      {
//        for (String cancerId : regimenDetail.getSubCancerTypeId3().split(",")) {
//          if (cancerId != "") {
//            Cancer cancer = cancerRepository.getCancerById(Integer.valueOf(cancerId));
//
//            if(null == cancer.getRegimen()) {
//              cancer.setRegimen(regimenDetail.getId() + "");
//            }
//            else if (cancer.getRegimen().indexOf(regimenDetail.getId()) == -1) {
//              cancer.setRegimen(cancer.getRegimen() + "," + regimenDetail.getId());
//            }
//
//            cancerRepository.save(cancer);
//          }
//        }
//      }
    }

  public CancerResponse getRegimenDetailByCancerId(String cancerId) {

    CancerResponse cancerResponse = new CancerResponse();

    if (Integer.valueOf(cancerId) == 0) {
      cancerResponse.setRegimenDetail(regimenDetailRepository.getAllRegimenDetails());
    } else {
      List<RegimenDetail> regimenFromCancer = new ArrayList<>();
      cancerResponse.setRegimenDetail(regimenFromCancer);
    }

    cancerResponse.setParentCancers(cancerService.getParentCancers(Integer.valueOf(cancerId)));

    if (null != cancerResponse.getParentCancers() && cancerResponse.getParentCancers().size() > 0) {
      cancerResponse.setPatientType(cancerResponse.getParentCancers().get(0).getPatientType());
      cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(cancerResponse.getPatientType()));
    }

    return cancerResponse;
  }

    public CancerResponse getRegimenDetailByCancerIdAndType(String cancerId, String type) {

        CancerResponse cancerResponse = new CancerResponse();

        cancerResponse.setParentCancers(cancerService.getParentCancers(Integer.valueOf(cancerId)));

        if(null != cancerResponse.getParentCancers() && cancerResponse.getParentCancers().size() > 0) {
          cancerResponse.setPatientType(cancerResponse.getParentCancers().get(0).getPatientType());
          cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(cancerResponse.getPatientType()));
        }

        return cancerResponse;
    }


    public int getRegimenId(RegimenDetail regimenDetail1) {

        if (regimenDetail1.getId() != 0) {
            return regimenDetail1.getId();
        }
        return regimenDetailRepository.getMaxId() + 1;
    }

  public List<LevelType> getLevels() {

    List<LevelType> levels = new ArrayList<>();
    levels.addAll(levelTypeRepository.getLevels());
    return levels;
  }


    public RegimenDetail updateCancerToRegimenList(Integer regimenId, String cancerIds) {
//      RegimenDetail regimenDetailToUpdate = this.regimenDetailRepository.getRegimenDetailWithId(regimenId);
//
//      String cancersInRegimen = StringUtils.isEmpty(regimenDetailToUpdate.getSubCancerTypeId3()) ?  "" : regimenDetailToUpdate.getSubCancerTypeId3();
//
//      for(String cancerId : cancerIds.split(",")) {
//        if (Arrays.asList(cancersInRegimen.split(",")).indexOf(cancerId) > -1) {
//          cancersInRegimen = Arrays.stream(cancersInRegimen.split(",")).filter(id -> !id.equals(cancerId)).collect(Collectors.joining(","));
//        } else {
//          cancersInRegimen = cancersInRegimen.length() > 0 ? cancersInRegimen + "," + cancerId : cancerId;
//        }
//      }
//
//      regimenDetailToUpdate.setSubCancerTypeId3(cancersInRegimen);
//
//      this.regimenDetailRepository.save(regimenDetailToUpdate);
//
//      return regimenDetailToUpdate;
        return null;
    }

}
