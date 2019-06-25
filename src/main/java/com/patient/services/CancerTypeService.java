package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.CancerType;
import com.patient.repos.CancerTypeRepository;
import com.patient.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CancerTypeService {

    @Autowired
    private CancerTypeRepository cancerTypeRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private ObjectMapper objectMapper;

    public List<CancerType> getAllCancerTypes(){
        return cancerTypeRepository.findAll();
    }

    public CancerType getCancerTypeId(int id){
        return cancerTypeRepository.findOne(id);
    }

    public  List<CancerType> getCancerTypes(int id) { return
            cancerTypeRepository.findByCancerTypeId(id);}

    public int deleteCancerType(int id) {
        CancerType cancerType = cancerTypeRepository.findOne(id);
        cancerTypeRepository.delete(cancerType.getId());
        return id;
    }


    public CancerType addOrUpdateCancerType(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        CancerType cancerType1 = objectMapper.readValue(payLoad, CancerType.class);

        CancerType cancerType = CancerType.builder()
                .id(cancerType1.getId() != 0? cancerType1.getId() :cancerTypeRepository.getMaxId() +1)
                .patient(patientRepository.findOne(cancerType1.getPatienttypeid()))
                .patienttypeid(cancerType1.getPatienttypeid())
                .title(cancerType1.getTitle())
                .build();

        return cancerTypeRepository.save(cancerType);
    }

    public  CancerType updateCancerType( String payLoad)  throws JsonParseException, JsonMappingException, IOException{
        CancerType cancerType1 = objectMapper.readValue(payLoad, CancerType.class);
        CancerType cancerType = cancerTypeRepository.findOne(cancerType1.getId());

        if(cancerType.getId() != cancerType1.getId()
                | cancerType.getPatienttypeid() != cancerType1.getPatienttypeid()
                | cancerType.getTitle() != cancerType1.getTitle()){

            cancerType.setId(cancerType1.getId());
            cancerType.setTitle(cancerType1.getTitle());
            cancerType.setPatienttypeid(cancerType1.getPatienttypeid());

            return cancerTypeRepository.save(cancerType);
        }
        return cancerType;
    }



}
