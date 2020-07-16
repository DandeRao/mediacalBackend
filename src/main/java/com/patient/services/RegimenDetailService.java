package com.patient.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.models.*;
import com.patient.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
  private DrugRegimenLinkRepository drugRegimenLinkRepository;

  @Autowired
  private DrugBrandLinkRepository drugBrandLinkRepository;

  @Autowired
  private ReferenceRepository referenceRepository;

  @Autowired
  private RegimenLevelLinkRepository regimenLevelLinkRepository;

  @Autowired
  private CancerRegimenLinkRepository cancerRegimenLinkRepository;

  @Autowired
  private DrugRepository drugRepository;

  @Autowired
  private DrugBrandRepository drugBrandRepository;

  public List<RegimenDetail> getAllRegimenDetails() {
    return regimenDetailRepository.findAll();
  }

  public RegimenDetail getRegimenDetailId(int id) {
    return regimenDetailRepository.findOne((long) id);
  }

  public int deleteRegimenDetail(int id) {
    regimenDetailRepository.deleteRegimenWithId(id);
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

  public String editLevel(String payLoad) throws JsonParseException, JsonMappingException, IOException {
    List<LevelType> levels =  objectMapper.readValue(payLoad,
            objectMapper.getTypeFactory().constructCollectionType(List.class, LevelType.class));

    for(LevelType levelType: levels) {
      if (null == levelType.getId() && !StringUtils.isEmpty(levelType.getLevel())) {
        levelType.setId(levelTypeRepository.getNextId());
      }
    }

    levelTypeRepository.save(levels);

    return "SUCCESS";
  }

  public String deleteLevel(String payload) throws JsonParseException, JsonMappingException, IOException {
    List<LevelType> levels =  objectMapper.readValue(payload,
            objectMapper.getTypeFactory().constructCollectionType(List.class, LevelType.class));

    for (LevelType levelType: levels) {
      if (null != levelType.getId()) {
        regimenLevelLinkRepository.deleteRegimenLevelLinksByLevelId(levelType.getId());

        levelTypeRepository.delete(levelTypeRepository.getById(levelType.getId()));
      }
    }
    return "SUCCESS";
  }

  public RegimenDetail updateRegimenDetail(String payLoad, Integer cancerId) throws JsonParseException, JsonMappingException, IOException {
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

    if (null != cancerId) {
      addRegimenToCancer(regimenInDB.getId(), cancerId);
    }

    return regimenInDB;

  }

  public void linkRegimenToCancer(String payload) throws IOException {
    List<CancerRegimenLink> cancerRegimenLinks = objectMapper.readValue(payload,
            objectMapper.getTypeFactory().constructCollectionType(List.class, CancerRegimenLink.class));

    if (null != cancerRegimenLinks) {
      cancerRegimenLinks.forEach(cancerRegimenLink -> {
        addRegimenToCancer(cancerRegimenLink.getRegimenId(), cancerRegimenLink.getCancerId());
      });
    }
  }

  public void unLinkRegimenToCancer(String payload) throws IOException {
    List<CancerRegimenLink> cancerRegimenLinks = objectMapper.readValue(payload,
            objectMapper.getTypeFactory().constructCollectionType(List.class, CancerRegimenLink.class));

    if (null != cancerRegimenLinks) {
      cancerRegimenLinks.forEach(cancerRegimenLink -> {
        deleteRegimenFromCancer(cancerRegimenLink.getRegimenId(), cancerRegimenLink.getCancerId());
      });
    }
  }

  public void addRegimenToCancer(Integer regimenId, Integer cancerId) {
    CancerRegimenLink cancerRegimenLink = cancerRegimenLinkRepository.getCancerRegimenLinkByRegimenAndCancerId(regimenId, cancerId);

    if (null == cancerRegimenLink) {
      cancerRegimenLink = new CancerRegimenLink(cancerId, regimenId);
      cancerRegimenLink.setId(cancerRegimenLinkRepository.getMaxId() + 1);
      cancerRegimenLinkRepository.save(cancerRegimenLink);
    }
  }

  public void deleteRegimenFromCancer(Integer regimenId, Integer cancerId) {
    CancerRegimenLink cancerRegimenLink = cancerRegimenLinkRepository.getCancerRegimenLinkByRegimenAndCancerId(regimenId, cancerId);

    if (null != cancerRegimenLink) {
      cancerRegimenLinkRepository.delete(cancerRegimenLink);
    }
  }

  public void saveBrands(List<Drug> drugs, int regimenId) {
    Drug savedDrug = null;
    for (Drug drug : drugs) {
      if (null == drug.getId()) {
        drug.setId(drugRepository.getNextId());
        savedDrug = drug;
      } else {
        savedDrug = drugRepository.getById(drug.getId());
        savedDrug.setGenericName(drug.getGenericName());
      }

      savedDrug = drugRepository.saveAndFlush(savedDrug);

      DrugRegimenLink drugRegimenLink = drugRegimenLinkRepository.findDrugRegimenLinkByBrandIdAndRegimenId(savedDrug.getId(), regimenId);
      if (null == drugRegimenLink) {
        drugRegimenLink = new DrugRegimenLink(savedDrug.getId(), regimenId);
        drugRegimenLink.setId(null == drugRegimenLinkRepository.getMaxId() ? 0 : drugRegimenLinkRepository.getMaxId() + 1 );
        drugRegimenLinkRepository.save(drugRegimenLink);
      }

      for (DrugBrand brand: drug.getDrugBrandList()) {
        DrugBrand brandToSave = null;
        if (null == brand.getId()) {
          brand.setId(drugBrandRepository.getNextId());
          brandToSave = brand;
        } else {
          brandToSave = drugBrandRepository.getById(brand.getId());
          brandToSave.setBrandName(brand.getBrandName());
          brandToSave.setManufacturer(brand.getManufacturer());
        }

        drugBrandRepository.saveAndFlush(brandToSave);
      }

    }
  }

  private void saveRegimenLevels(List<LevelType> levels, int regimenId) {
    LevelType savedLevel = null;
    for (LevelType level : levels) {

      if (null == level.getId()) {
        level.setId(levelTypeRepository.getNextId());
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
                ? 0 : regimenLevelLinkRepository.getNextId());

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

  public CancerResponse getRegimenDetailByCancerId(int cancerId) {

    CancerResponse cancerResponse = new CancerResponse();

    if (cancerId == 0) {
      List<RegimenDetail> regimenDetails = regimenDetailRepository.getAllRegimenDetails();
      for (RegimenDetail regimenDetail: regimenDetails) {
        regimenDetail.setLinkedToCancers(cancerRepository.getCancerNamesLinkedToARegimenByRegimenId(regimenDetail.getId()));
      }

      cancerResponse.setRegimenDetail(regimenDetails);
    } else {
      cancerResponse.setRegimenDetail(regimenDetailRepository.getRegimenDetailByCancerId(cancerId));
    }

    cancerResponse.setParentCancers(cancerService.getParentCancers(cancerId));
    cancerResponse.setCurrentCancer(cancerRepository.getCancerById(cancerId));
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
      cancerResponse.setCurrentCancer(cancerRepository.getCancerById(Integer.valueOf(cancerId)));
      cancerResponse.setPatientTitle(patientRepository.getPatientTitileById(cancerResponse.getPatientType()));
      cancerResponse.setRegimenDetail(regimenDetailRepository.getByCancerIdAndRegimenLevelType(Integer.valueOf(cancerId), type));
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

  public CancerResponse getRegimenListToAddToCancer(int cancerId, String type) {
    CancerResponse response = new CancerResponse();

    if (!StringUtils.isEmpty(type)) {
      response.setExistingRegimenInCancer(regimenDetailRepository.getByCancerIdAndRegimenLevelType(cancerId, type));
    } else {
      response.setExistingRegimenInCancer(regimenDetailRepository.getRegimenDetailByCancerId(cancerId));
    }

    response.setAllRegimenListToAddToCancer(regimenDetailRepository.getRegimenDetailNotLinkedToCancerId(cancerId));

    return response;
  }

  public List<Drug> getAllDrugs() {
    return drugRepository.findAll(new Sort(Sort.Direction.ASC, "genericName"));
  }

  public Drug addDrug(String payload) throws IOException {

    Drug drugFromRequest = objectMapper.readValue(payload, Drug.class);

    if (null == drugFromRequest.getId()) {
      drugFromRequest.setId(drugRepository.getNextId());
    }

    Drug drugToSave = new Drug();
    drugToSave.setId(drugFromRequest.getId());
    drugToSave.setGenericName(drugFromRequest.getGenericName());
    drugRepository.save(drugToSave);

    if (null != drugFromRequest.getDrugBrandList()) {
      for(DrugBrand drugBrand: drugFromRequest.getDrugBrandList()) {
        if (null == drugBrand.getId()) {
          drugBrand.setId(drugBrandRepository.getNextId());
        }

        drugBrandRepository.save(drugBrand);

        DrugBrandLink drugBrandLink = new DrugBrandLink();
        drugBrandLink.setId(drugBrandLinkRepository.getNextId());
        drugBrandLink.setDrugId(drugFromRequest.getId());
        drugBrandLink.setDrugBrandId(drugBrand.getId());

        drugBrandLinkRepository.save(drugBrandLink);
      }
    }

    return drugToSave;
  }

  public Drug editDrug(String payload) throws IOException {

    Drug drugFromRequest = objectMapper.readValue(payload, Drug.class);
    Drug drugToSave = new Drug();
    drugToSave.setId(drugFromRequest.getId());
    drugToSave.setGenericName(drugFromRequest.getGenericName());
    drugRepository.save(drugToSave);

    if (null != drugFromRequest.getDrugBrandList()) {
      for(DrugBrand drugBrand: drugFromRequest.getDrugBrandList()) {
        if (null == drugBrand.getId()) {
          drugBrand.setId(drugBrandRepository.getNextId());
        }

        drugBrandRepository.save(drugBrand);

        if (null == drugBrandLinkRepository.getDrugBrandLinkByDrugAndBrandId(drugBrand.getId(), drugFromRequest.getId())) {
          DrugBrandLink drugBrandLink = new DrugBrandLink(drugFromRequest.getId(), drugBrand.getId());
          drugBrandLink.setId(drugBrandLinkRepository.getNextId());

          drugBrandLinkRepository.save(drugBrandLink);
        }
      }
    }

    return drugToSave;
  }
}
