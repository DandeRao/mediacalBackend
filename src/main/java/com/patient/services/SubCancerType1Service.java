package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.SubCancerType1;
import com.patient.models.SubCancerType3;
import com.patient.repos.CancerTypeRepository;
import com.patient.repos.SubCancerType3Repository;
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
    private SubCancerType3Repository type3Repository;

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

    public  List<SubCancerType1> getSubCancerType1TypesById(int patientId, int cancerId) {
        List<SubCancerType3> subCancersInLinkedTable = type3Repository.findSubCancerType1ById(patientId, cancerId);
        List<SubCancerType1> subCancerForCancer = subCancerTypeRepository.findSubCancerType1ById(cancerId);

        for(SubCancerType3 subCancer :subCancersInLinkedTable)
        {
            SubCancerType1 subCancer1 = new SubCancerType1();
            subCancer1.setId(subCancer.getId());
            subCancer1.setPatienttypeid(subCancer.getPatienttypeid());
             subCancer1.setSubcancertypeid(subCancer.getSubcancertype1id());
            subCancer1.setTitle(subCancer.getTitle());
            subCancerForCancer.add(subCancer1);
        }



        return subCancerForCancer;
            }


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
                .patienttypeid(subCancerType1.getPatienttypeid())
                .title(subCancerType1.getTitle())
                .build();
        return  subCancerTypeRepository.save(subCancerType);
    }

}
