package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.SubCancerType3;
import com.patient.repos.SubCancerType2Repository;
import com.patient.repos.SubCancerType3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SubCancerType3Service {

    @Autowired
    private SubCancerType3Repository subCancerType3Repository;

    @Autowired
    private SubCancerType2Repository subCancerType2Repository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<SubCancerType3> getAllSubCancerType3(){
        return subCancerType3Repository.findAll();
    }

    public SubCancerType3 getSubCancerType2Id(int id){
        return subCancerType3Repository.findOne(id);
    }

    public  List<SubCancerType3> getSubCancerType3TypesById(String payLoad)  throws JsonParseException, JsonMappingException, IOException {
        SubCancerType3 subCancerType3 = objectMapper.readValue(payLoad, SubCancerType3.class);
        return subCancerType3Repository.findSubCancerTypeByLinkedId(subCancerType3.getPatienttypeid(), subCancerType3.getCancerTypeId(), subCancerType3.getSubcancertype1id(), subCancerType3.getSubcancertype2id(), subCancerType3.getLinkedSubCancerId());
    }


    public long deleteSubCancerType3(int id) {
        subCancerType3Repository.delete(subCancerType3Repository.findOne(id).getId());
        return id;
    }

    public SubCancerType3 addOrUpdateSubCancerType3(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        SubCancerType3 subCancerType3 = objectMapper.readValue(payLoad, SubCancerType3.class);
        int newId = (subCancerType3.getId() !=0)? subCancerType3.getId() : (0 == subCancerType3Repository.getMaxId() ? 1 : subCancerType3Repository.getMaxId() +1);
        SubCancerType3 subCancerType3new = SubCancerType3.builder()
                    .id(newId)
                    .title(subCancerType3.getTitle())
                    .patienttypeid(subCancerType3.getPatienttypeid())
                    .cancerTypeId(subCancerType3.getCancerTypeId())
                    .subcancertype1id(subCancerType3.getSubcancertype1id())
                    .subcancertype2id(subCancerType3.getSubcancertype2id())
                    .linkedSubCancerId(subCancerType3.getLinkedSubCancerId())
                    .build();


        return subCancerType3Repository.save(subCancerType3new);
    }

}
