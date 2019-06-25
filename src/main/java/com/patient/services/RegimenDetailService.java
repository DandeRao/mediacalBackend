package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.RegimenDetail;
import com.patient.models.SubCancerType1;
import com.patient.models.SubCancerType2;
import com.patient.models.SubCancerType3;
import com.patient.repos.RegimenDetailRepository;
import com.patient.repos.SubCancerType2Repository;
import com.patient.repos.SubCancerType3Repository;
import com.patient.repos.SubCancerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RegimenDetailService {

    @Autowired
    private RegimenDetailRepository regimenDetailRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SubCancerType3Repository subCancerType3Repository;

    @Autowired
    private SubCancerType2Repository subCancerType2Repository;

    @Autowired
    private SubCancerTypeRepository subCancerTypeRepository;

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
        SubCancerType1 subCancerType1 = subCancerTypeRepository.findOne(regimenDetail1.getId2());
        SubCancerType2 subCancerType2 = subCancerType2Repository.findOne(regimenDetail1.getId3());
        SubCancerType3 subCancerType3 = subCancerType3Repository.findOne(regimenDetail1.getId4());
        RegimenDetail regimenDetail = RegimenDetail.builder()
                .id(getRegimenId(regimenDetail1))
                .brandNames(regimenDetail1.getBrandNames())
                .dispName(regimenDetail1.getDispName())
                .emetogenicPotential(regimenDetail1.getEmetogenicPotential())
                .dispName(regimenDetail1.getDispName())
                .dosageModifications(regimenDetail1.getDosageModifications())
                .schedule(regimenDetail1.getSchedule())
                .subCancerType3(subCancerType3)
                .subCancerType2(subCancerType2)
                .subCancerType1(subCancerType1)
                .SubCancerTypeId3(regimenDetail1.getId2())
                .build();
        return regimenDetailRepository.save(regimenDetail);
    }

    public RegimenDetail updateRegimenDetail(String payLoad) throws JsonParseException, JsonMappingException, IOException {
        RegimenDetail regimenDetail1 = objectMapper.readValue(payLoad, RegimenDetail.class);
        RegimenDetail regimenDetail = regimenDetailRepository.findOne((long) regimenDetail1.getSubCancerTypeId3());

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
            return regimenDetailRepository.save(regimenDetail);
        }






        return regimenDetail;
    }


    public  List<RegimenDetail> getRegimenDetailById(int id) { return
            regimenDetailRepository.findRegimenDetailById(id);}

    public long getRegimenId( RegimenDetail regimenDetail1){

        if(regimenDetail1.getId() != null){
            if(regimenDetail1.getId() != 0){
                return  regimenDetail1.getId();
            }
        }
        return regimenDetailRepository.getMaxId() + 1;
    }

}
