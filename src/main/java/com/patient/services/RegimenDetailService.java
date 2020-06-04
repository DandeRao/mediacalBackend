package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.*;
import com.patient.repos.*;
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

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private BrandRegimenLinkRepository brandRegimenLinkRepository;

  @Autowired
  private ReferenceRepository referenceRepository;

  @Autowired
  private RegimenLevelLinkRepository regimenLevelLinkRepository;

  public List<RegimenDetail> getAllRegimenDetails() {
    return regimenDetailRepository.findAll();
  }

  public RegimenDetail getRegimenDetailId(int id) {
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

    if (null == existingLevel) {
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
    RegimenDetail regimenInRequest = objectMapper.readValue(payLoad, RegimenDetail.class);


    RegimenDetail regimenInDB = null;

    if (null == regimenInRequest.getId()) {
      regimenInDB = new RegimenDetail();
      regimenInDB.setId(regimenDetailRepository.getMaxId() + 1);
    } else {
      regimenInDB = regimenDetailRepository.fingRegimenById(regimenInRequest.getId());
    }

    regimenInDB.setDispName(regimenInRequest.getDispName());
    regimenInDB.setDosageModifications(regimenInRequest.getDosageModifications());
    regimenInDB.setEmetogenicPotential(regimenInRequest.getEmetogenicPotential());
    regimenInDB.setSchedule(regimenInRequest.getSchedule());

    regimenInDB = regimenDetailRepository.save(regimenInDB);

    saveRegimenInCancer(regimenInDB);

    if (null != regimenInRequest.getBrands() && regimenInRequest.getBrands().size() > 0) {

      saveBrands(regimenInRequest.getBrands(), regimenInDB.getId());
    }

    if (null != regimenInRequest.getRegimenLevels() && regimenInRequest.getRegimenLevels().size() > 0) {
      saveRegimenLevels(regimenInRequest.getRegimenLevels(), regimenInDB.getId());
    }

    if (null != regimenInRequest.getReferences() && regimenInRequest.getReferences().size() > 0) {
      saveReferences(regimenInRequest.getReferences(), regimenInDB.getId());
    }

    return regimenInDB;

  }

  public void saveBrands(List<Brand> brands, int regimenId) {
    Brand savedBrand = null;
    for (Brand brand : brands) {
      if (null == brand.getId()) {
        brand.setId(brandRepository.getMaxId() + 1);
        savedBrand = brand;
      } else {
        savedBrand = brandRepository.getById(brand.getId());
        savedBrand.setBrandName(brand.getBrandName());
        savedBrand.setGenericName(brand.getGenericName());
        savedBrand.setManufacturer(brand.getManufacturer());
      }

      savedBrand = brandRepository.saveAndFlush(savedBrand);

      BrandRegimenLink brandRegimenLink = brandRegimenLinkRepository.findBrandRegimenLinkByBrandIdAndRegimenId(savedBrand.getId(), regimenId);
      if (null == brandRegimenLink) {
        brandRegimenLink = new BrandRegimenLink(savedBrand.getId(), regimenId);
        brandRegimenLink.setId(null == brandRegimenLinkRepository.getMaxId() ? 0 : brandRegimenLinkRepository.getMaxId() + 1 );
        brandRegimenLinkRepository.save(brandRegimenLink);
      }
    }
  }

  private void saveRegimenLevels(List<LevelType> levels, int regimenId) {
    LevelType savedLevel = null;
    for (LevelType level : levels) {

      if (null == level.getId()) {
        level.setId(levelTypeRepository.getMaxId() + 1);
        savedLevel = level;
      } else {
        savedLevel = levelTypeRepository.getById(level.getId());
        savedLevel.setLevel(level.getLevel());
      }

      savedLevel = levelTypeRepository.save(savedLevel);

      RegimenLevelLink regimenLevelLink = regimenLevelLinkRepository.findRegimenLevelLinkByLevelIdAndRegimenId(savedLevel.getId(), regimenId);
      if (null == regimenLevelLink) {
        regimenLevelLink = new RegimenLevelLink(savedLevel.getId(), regimenId);

        regimenLevelLink.setId( null == regimenLevelLinkRepository.getMaxId()
                ? 0 : regimenLevelLinkRepository.getMaxId() + 1);

        regimenLevelLinkRepository.save(regimenLevelLink);
      }
    }
  }

  public void saveReferences(List<Reference> references, int regimenId) {
    List<Reference> referencesForRegimen = referenceRepository.getByRegimenId(regimenId);
    referenceRepository.delete(referencesForRegimen);

    if (null != references && references.size() > 0) {
      for (Reference reference: references) {
        reference.setId(referenceRepository.getMaxId() + 1);
        reference.setReferenceValue(reference.getReferenceValue());
        reference.setRegimenDetail(regimenDetailRepository.getById(regimenId));
        referenceRepository.save(reference);
      }
    }

  }
  private void saveRegimenInCancer(RegimenDetail regimenDetail) {
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

    if (null != cancerResponse.getParentCancers() && cancerResponse.getParentCancers().size() > 0) {
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
