package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.SubCancerType1;
import com.patient.repos.CancerTypeRepository;
import com.patient.repos.SubCancerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SubCancerType1Service {

    @Autowired
    private SubCancerTypeRepository subCancerTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CancerTypeRepository cancerTypeRepository;

    public List<SubCancerType1> getAllSubCancerTypes(){
        return subCancerTypeRepository.findAll();
    }

    public SubCancerType1 getSubCancerTypeId(int id){
        return subCancerTypeRepository.findOne(id);
    }

    public  List<SubCancerType1> getSubCancerType1TypesById(int id) { return
            subCancerTypeRepository.findSubCancerType1ById(id);}


    public long deleteSubCancerType1(int id) {
        SubCancerType1 subCancerType1 = subCancerTypeRepository.findOne(id);
        subCancerTypeRepository.delete(subCancerType1.getId());
        return id;
    }

    public SubCancerType1 addOrUpdateSubCancerType1(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        SubCancerType1 subCancerType1 = objectMapper.readValue(payLoad, SubCancerType1.class);
        SubCancerType1 subCancerType = SubCancerType1.builder()
                .id(subCancerType1.getId() != 0? subCancerType1.getId() :subCancerTypeRepository.getMaxId() + 1)
                .subcancertypeid(subCancerType1.getSubcancertypeid())
                .cancerType(cancerTypeRepository.findOne(subCancerType1.getSubcancertypeid()))
                .title(subCancerType1.getTitle())
                .build();
        return  subCancerTypeRepository.save(subCancerType);
    }

}
