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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public long deleteRegimenDetail(long id) {
        RegimenDetail regimenDetail = regimenDetailRepository.findOne(id);
        regimenDetailRepository.delete(regimenDetail);
        return id;
    }

    public RegimenDetail addOrUpdateRegimenDetail(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        RegimenDetail regimenDetail1 = objectMapper.readValue(payLoad, RegimenDetail.class);

        RegimenDetail regimenDetail = RegimenDetail.builder()
                .id(getRegimenId(regimenDetail1))
                .brandNames(regimenDetail1.getBrandNames())
                .dispName(regimenDetail1.getDispName())
                .emetogenicPotential(regimenDetail1.getEmetogenicPotential())
                .dispName(regimenDetail1.getDispName())
                .dosageModifications(regimenDetail1.getDosageModifications())
                .schedule(regimenDetail1.getSchedule())
                .regimenType(regimenDetail1.getRegimenType())
                .build();

      String regimenForCancer = null;
      Integer cancerId = null;

      if(null != regimenDetail1.getSubCancerTypeId3())
      {
        cancerId = regimenDetail1.getSubCancerTypeId3();

        regimenDetail.setSubCancerTypeId3(cancerId);

        regimenForCancer = cancerRepository.getRegimenByPatientId(cancerId);
      }

        if((regimenForCancer != null) && (null != cancerId )&& (regimenForCancer.indexOf(cancerId + "") < 0)) {
            regimenForCancer =  regimenForCancer.concat("," + getRegimenId(regimenDetail1) + "");
            Cancer cancer = cancerRepository.getCancerById(cancerId);
            cancer.setRegimen(regimenForCancer);
            cancerRepository.save(cancer);
        }

        return regimenDetailRepository.save(regimenDetail);
    }

    public LevelType addLevel(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        LevelType level = objectMapper.readValue(payLoad, LevelType.class);

        LevelType levelDetail = LevelType.builder()
                .id(levelTypeRepository.getMaxId() + 1)
                .level(level.getLevel())
                .type(level.getType())
                .build();

        return levelTypeRepository.save(levelDetail);
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
            if (null != regimenDetail1.getSubCancerTypeId3()) {
              regimenDetail.setSubCancerTypeId3(regimenDetail1.getSubCancerTypeId3());
            }
            return regimenDetailRepository.save(regimenDetail);
        }






        return regimenDetail;
    }


    public CancerResponse getRegimenDetailByCancerId(int cancerId) {

        CancerResponse cancerResponse = new CancerResponse();

        if (cancerId == 0) {
            cancerResponse.setRegimenDetail(regimenDetailRepository.getAllRegimenDetails());
        } else {
            cancerResponse.setRegimenDetail(regimenDetailRepository.findRegimenDetailByCancerId(cancerId));
        }

        cancerResponse.setParentCancers(cancerService.getParentCancers(cancerId));

        if(null != cancerResponse.getParentCancers() && cancerResponse.getParentCancers().size() > 0) {
            cancerResponse.setPatientType(cancerResponse.getParentCancers().get(0).getPatientType());
            cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(cancerResponse.getPatientType()));
        }

        return cancerResponse;
    }

    public CancerResponse getRegimenDetailByCancerIdAndType(int cancerId, String type) {

        CancerResponse cancerResponse = new CancerResponse();

        cancerResponse.setRegimenDetail(regimenDetailRepository.findRegimenDetailByIdAndType(cancerId, type));

        cancerResponse.setParentCancers(cancerService.getParentCancers(cancerId));

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


    public List<String> getLevelsByType(String type) {

        List<String> levels = new ArrayList<>();

        for(LevelType level: levelTypeRepository.findLevelTypeByType(type)) {
            levels.add(level.getLevel());
        }

        return levels;
    }
}
