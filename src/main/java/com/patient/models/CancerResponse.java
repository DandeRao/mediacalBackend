package com.patient.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CancerResponse {
  private int id;

  private int patientType;

  private Cancer currentCancer;

  private String patientTitle;

  private Integer parentId;

  private String title;

  private List<RegimenDetail> regimenDetail;

  private List<RegimenDetail> allRegimenListToAddToCancer;

  private List<RegimenDetail> existingRegimenInCancer;

  private List<Cancer> parentCancers;

  private List<Cancer> subCancers;

  private List<Object> allCancers;
}
