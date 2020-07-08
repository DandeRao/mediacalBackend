package com.patient.services;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.Cancer;
import com.patient.models.CancerRegimenLink;
import com.patient.models.CancerResponse;
import com.patient.models.RegimenDetail;
import com.patient.models.responses.Regimen;
import com.patient.repos.CancerRegimenLinkRepository;
import com.patient.repos.CancerRepository;
import com.patient.repos.PatientRepository;
import com.patient.repos.RegimenDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@ComponentScan("com.patient.services")
@Service
public class CancerService {


  @Autowired
  private CancerRepository cancerRepository;

  @Autowired
  private RegimenDetailRepository regimenDetailRepository;

  @Autowired
  private PatientRepository patientRepository;

  @Autowired
  private CancerRegimenLinkRepository cancerRegimenLinkRepository;

  @Autowired
  private ObjectMapper objectMapper;


  public CancerResponse getCancersWithPatientId(int patientId) {

    List<Cancer> cancers = cancerRepository.getCancersByPatientId(patientId);

    CancerResponse cancerResponse = new CancerResponse();
    cancerResponse.setPatientType(patientId);
    cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(patientId));
    cancerResponse.setSubCancers(populateCancersWithRegimens(cancers));

    return cancerResponse;
  }

  public CancerResponse getCancersWithParentId(int parentId) {
    List<Cancer> cancers = cancerRepository.getCancersByParentId(parentId);

//    populateCancersWithSubCancerTypes(cancers);

    CancerResponse cancerResponse = new CancerResponse();
    cancerResponse.setCurrentCancer(cancerRepository.getCancerById(parentId));
    cancerResponse.setId(parentId);
    cancerResponse.setSubCancers(populateCancersWithRegimens(cancers));
    cancerResponse.setParentCancers(getParentCancers(parentId));

    List<RegimenDetail> regimenForCancer = new ArrayList<>();
    regimenForCancer.addAll(getRegimenDetailFromRegimenList(cancerResponse.getCurrentCancer().getId()));
    cancerResponse.setRegimenDetail(regimenForCancer);

    if(null != cancerResponse.getSubCancers() && cancerResponse.getSubCancers().size() > 0) {
      cancerResponse.setPatientType(cancerResponse.getSubCancers().get(0).getPatientType());
      cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(cancerResponse.getPatientType()));
    }

    return cancerResponse;
  }

  public boolean deleteCancerId(int id) {

    try{
      cancerRepository.deleteCancersWithParentId(id);
      cancerRepository.delete(id);
    }
    catch (Exception e) {
      return false;
    }

    return true;
  }


  public CancerResponse addOrUpdateCancer(String payLoad) throws JsonParseException, JsonMappingException, IOException {

    Cancer incomingCancer = objectMapper.readValue(payLoad, Cancer.class);
    int newId = (null != incomingCancer.getId() && incomingCancer.getId() !=0)
            ? incomingCancer.getId() : (0 ==  cancerRepository.getMaxId() ? 1 : cancerRepository.getMaxId() +1);

    Cancer cancer = cancerRepository.save(Cancer.builder()
            .id(newId)
            .title(incomingCancer.getTitle())
            .patientType(incomingCancer.getPatientType())
            .parentId(incomingCancer.getParentId())
            .regimen(incomingCancer.getRegimen())
            .build());

    CancerResponse cancerResponse = new CancerResponse();
    cancerResponse.setId(cancer.getId());
    cancerResponse.setTitle(cancer.getTitle());
    cancerResponse.setPatientType(cancer.getPatientType());
    cancerResponse.setRegimenDetail(getRegimenDetailFromRegimenList(cancer.getId()));
    cancerResponse.setParentId(cancer.getParentId());

    return cancerResponse;
  }

  public CancerResponse updateRegimenInCancer(String payLoad) throws JsonParseException, JsonMappingException, IOException {

    Cancer incomingCancer = objectMapper.readValue(payLoad, Cancer.class);
    String incomingRegimenString = !StringUtils.isEmpty(incomingCancer.getRegimen()) ? incomingCancer.getRegimen() : "";

    List<CancerRegimenLink> existingCancerRegimen = cancerRegimenLinkRepository.findRegimenDetailByCancerId(incomingCancer.getId());

    List<CancerRegimenLink> regimenBeingRemoved = new ArrayList<>();
    List<CancerRegimenLink> finalRegimenList = new ArrayList<>();

    for(String regimenId: incomingRegimenString.split(",")) {
      int numberOfRegimenBeingRemoved = regimenBeingRemoved.size();
      for(CancerRegimenLink cancerRegimenLink: existingCancerRegimen) {
        if (cancerRegimenLink.getRegimenId().equals(Integer.valueOf(regimenId))) {
          regimenBeingRemoved.add(cancerRegimenLink);
        }
      }

      if (numberOfRegimenBeingRemoved == regimenBeingRemoved.size()) {
          finalRegimenList.add(CancerRegimenLink.builder()
                  .id(cancerRegimenLinkRepository.getMaxId())
                  .regimenId(Integer.valueOf(regimenId))
                  .cancerId(incomingCancer.getId())
                  .build());
      }
    }

    if (regimenBeingRemoved.size() > 0) {
      cancerRegimenLinkRepository.delete(regimenBeingRemoved);
    }


    if (finalRegimenList.size() > 0) {
      cancerRegimenLinkRepository.save(finalRegimenList);
    }

    Cancer cancer = cancerRepository.getCancerById(incomingCancer.getId());

    CancerResponse cancerResponse = new CancerResponse();
    cancerResponse.setId(cancer.getId());
    cancerResponse.setTitle(cancer.getTitle());
    cancerResponse.setPatientType(cancer.getPatientType());
    cancerResponse.setRegimenDetail(getRegimenDetailFromRegimenList(cancer.getId()));
    cancerResponse.setParentId(cancer.getParentId());

    return cancerResponse;
  }

  public List<Cancer> getParentCancers(int parentId) {
    List<Cancer> parentCancers = new ArrayList<>();

    if(parentId == 0) {
      return  parentCancers;
    } else {
      Cancer cancer = cancerRepository.getCancerById(parentId);

      parentCancers.add(cancer);
      if(null!=cancer.getParentId() && cancer.getParentId() !=0 ) {
        parentCancers.addAll(getParentCancers(cancer.getParentId()));
      }
    }

    Collections.reverse(parentCancers);

    return  parentCancers;
  }

  private List<Cancer> populateCancersWithRegimens(List<Cancer> cancers) {

    for (Cancer cancer : cancers) {
      cancer.setRegimenLevelsInCancer(regimenDetailRepository.getRegimenLevelNameByCancerId(cancer.getId()));

      List<RegimenDetail> regimenForCancer = new ArrayList<>();
      if (!StringUtils.isEmpty(cancer.getRegimen())) {
        regimenForCancer.addAll(getRegimenDetailFromRegimenList(cancer.getId()));
      }

//      cancer.setRegimenDetails(regimenForCancer);
    }

    return cancers;
  }

  public List<RegimenDetail> getRegimenDetailFromRegimenList(int cancerId) {

    List<RegimenDetail> regimensForCancer = new ArrayList<>();


    List<Integer> regimenIdsWithCancer = new ArrayList<>();

    for (CancerRegimenLink cancerRegimenLink : cancerRegimenLinkRepository.findRegimenDetailByCancerId(cancerId)) {
      if (!StringUtils.isEmpty(cancerRegimenLink.getRegimenId())) {
        regimenIdsWithCancer.add(cancerRegimenLink.getRegimenId());
      }
    }

    if (!regimenIdsWithCancer.isEmpty()) {
      regimensForCancer.addAll(regimenDetailRepository.getRegimenFromListOfIds(regimenIdsWithCancer));
    }

    return  regimensForCancer;
  }

  public CancerResponse getAllCancerNames() {
    List<Object> cancers = cancerRepository.getAllCancerNames();
    CancerResponse response = new CancerResponse();
    response.setAllCancers(cancers);
    return  response;
  }


  public List<com.patient.models.responses.Cancer> getCancerResponseByPatientId(Integer patientId) {
    List<Cancer> cancers = cancerRepository.getCancersByPatientId(patientId);
    List<com.patient.models.responses.Cancer> cancersInResponse = getCancerResponsesFromCancers(cancers);

    for (com.patient.models.responses.Cancer cancer: cancersInResponse) {
      cancer.setSubCancers(getSubCancerResponseType(cancer));
    }

    return cancersInResponse;
  }


  public List<com.patient.models.responses.Cancer> getSubCancerResponseType(com.patient.models.responses.Cancer cancer) {
    List<com.patient.models.responses.Cancer> cancersInResponse = new ArrayList<>();

    for (Cancer cancerType : getSubCancersForCancer(cancer.getId())) {
      com.patient.models.responses.Cancer c1 = getCancerResponseTypeFromCancer(cancerType);
      List<Cancer> subCancers = getSubCancersForCancer(c1.getId());
      if (subCancers.size() == 0) {
        return cancersInResponse;
      } else {
        c1.setSubCancers(getSubCancerResponseType(c1));
        cancersInResponse.add(c1);
      }
    }

    return cancersInResponse;
  }

  public List<com.patient.models.responses.Cancer> getCancerResponsesFromCancers(List<Cancer> cancers) {
    List<com.patient.models.responses.Cancer> cancersInResponse = new ArrayList();

    for (Cancer cancer: cancers) {
      cancersInResponse.add(getCancerResponseTypeFromCancer(cancer));
    }

    return cancersInResponse;
  }

  public List<Cancer> getSubCancersForCancer(Integer cancerId) {
    return  cancerRepository.getCancersByParentId(cancerId);
  }

  public com.patient.models.responses.Cancer getCancerResponseTypeFromCancer(Cancer cancer) {
    com.patient.models.responses.Cancer cancerR = new com.patient.models.responses.Cancer();
    cancerR.setId(cancer.getId());
    cancerR.setTitle(cancer.getTitle());
    List<Regimen> regimenList = new ArrayList();

    for (RegimenDetail regimenDetail: cancer.getRegimenList()) {
      Regimen regimen = new Regimen();
      regimen.setId(regimenDetail.getId());
      regimenList.add(regimen);
    }

    cancerR.getRegimenList().addAll(regimenList);

    return cancerR;
  }
}
