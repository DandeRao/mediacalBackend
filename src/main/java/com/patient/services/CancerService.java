package com.patient.services;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.Cancer;
import com.patient.models.CancerResponse;
import com.patient.models.RegimenDetail;
import com.patient.repos.CancerRepository;
import com.patient.repos.PatientRepository;
import com.patient.repos.RegimenDetailRepository;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    cancerResponse.setId(parentId);
    cancerResponse.setSubCancers(populateCancersWithRegimens(cancers));
    cancerResponse.setParentCancers(getParentCancers(parentId));
    cancerResponse.setRegimenDetail(getRegimenForCancerId(String.valueOf(parentId)));
    if(null != cancerResponse.getSubCancers() && cancerResponse.getSubCancers().size() > 0) {
      cancerResponse.setPatientType(cancerResponse.getSubCancers().get(0).getPatientType());
      cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(cancerResponse.getPatientType()));
    }

    return cancerResponse;
  }

  public boolean deleteCancerId(int id) {

    try{
      cancerRepository.delete(id);
    }
    catch (Exception e) {
      return false;
    }

    return true;
  }


  public CancerResponse addOrUpdateCancer(String payLoad) throws JsonParseException, JsonMappingException, IOException {

    Cancer incomingCancer = objectMapper.readValue(payLoad, Cancer.class);
    int newId = (incomingCancer.getId() !=0)? incomingCancer.getId() : (0 ==  cancerRepository.getMaxId() ? 1 : cancerRepository.getMaxId() +1);

    Cancer cancer = cancerRepository.save(Cancer.builder()
            .id(newId)
            .title(incomingCancer.getTitle())
            .patientType(incomingCancer.getPatientType())
            .parentId(incomingCancer.getParentId())
            .regimen(incomingCancer.getRegimen())
            .subCancerType(incomingCancer.getSubCancerType())
            .build());

    CancerResponse cancerResponse = new CancerResponse();
    cancerResponse.setId(cancer.getId());
    cancerResponse.setTitle(cancer.getTitle());
    cancerResponse.setPatientType(cancer.getPatientType());
    cancerResponse.setRegimenDetail(getRegimenDetailFromRegimenList(cancer.getRegimen()));
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
      if (null != cancer.getRegimen() && !cancer.getRegimen().equals("")) {
        cancer.setRegimenDetails(getRegimenDetailFromRegimenList(cancer.getRegimen()));
      }

    }

    return cancers;
  }

  private List<RegimenDetail> getRegimenForCancerId(String cancerId) {

    return regimenDetailRepository.findRegimenDetailByCancerId(cancerId);
  }

  private List<RegimenDetail> getRegimenDetailFromRegimenList(String regimenString) {

    List<RegimenDetail> regimensForCancer = new ArrayList<>();

    if (null != regimenString && !regimenString.equals("")) {
      String[] regimens = regimenString.split(",");
      for (String regimen : regimens) {
        if(null != regimen && !regimen.equals("")) {
          RegimenDetail regimenDetail = regimenDetailRepository.getRegimenDetailWithId(Integer.parseInt(regimen));
          regimensForCancer.add(regimenDetail);
        }
      }
    }

    return  regimensForCancer;
  }

  public CancerResponse getAllCancerNames() {
    List<Object> cancers = cancerRepository.getAllCancerNames();
    CancerResponse response = new CancerResponse();
    response.setAllCancers(cancers);
    return  response;
  }
}
