package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.SubCancerType2;
import com.patient.models.SubCancerType3;
import com.patient.repos.SubCancerType2Repository;
import com.patient.repos.SubCancerType3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SubCancerType2Service {

    @Autowired
    private SubCancerType2Repository subCancerType2Repository;

    @Autowired
    private SubCancerType3Repository subCancerType3Repository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<SubCancerType2> getAllSubCancerType2(){
        return subCancerType2Repository.findAll();
    }

    public  List<SubCancerType2> getSubCancerType2TypesById(int patientId, int cancerId, int subCancer1Id) {

        List<SubCancerType3> subCancerType3LinkedList = subCancerType3Repository.findSubCancerType2ById(patientId, cancerId, subCancer1Id);
        List<SubCancerType2> subCancerType2 = subCancerType2Repository.findSubCancerType2ById(subCancer1Id);

        for(SubCancerType3 subCancer  : subCancerType3LinkedList){
            SubCancerType2 subCancerType2new = new SubCancerType2();
            subCancerType2new.setId(subCancer.getId());
            subCancerType2new.setTitle(subCancer.getTitle());
            subCancerType2new.setCancerTypeId(subCancer.getCancerTypeId());
            subCancerType2new.setPatienttypeid(subCancer.getPatienttypeid());
            subCancerType2.add(subCancerType2new);
        }

        return subCancerType2;

    }


    public long deleteSubCancerType2(int id) {
        subCancerType2Repository.delete(subCancerType2Repository.findOne(id).getId());
        return id;
    }

    public SubCancerType2 addOrUpdateSubCancerType2(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        SubCancerType2 subCancerType2 = objectMapper.readValue(payLoad, SubCancerType2.class);
        SubCancerType2 subCancerType1 = SubCancerType2.builder()
                .id(subCancerType2.getId() != 0? subCancerType2.getId() :(int) (subCancerType2Repository.getMaxId() +1))
                .subcancertypeid(subCancerType2.getSubcancertypeid())
                .cancerTypeId(subCancerType2.getSubcancertypeid())
                .patienttypeid(subCancerType2.getSubcancertypeid())
                .title(subCancerType2.getTitle())
                .build();
        return subCancerType2Repository.save(subCancerType1);
    }

}
