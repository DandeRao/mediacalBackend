package com.patient.models;

import java.util.List;

public class CancerResponse {
  private int id;

  private int patientType;

  private Integer parentId;

  private String title;

  private List<RegimenDetail> regimenDetail;

  private List<Cancer> parentCancers;

  private List<Cancer> subCancers;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPatientType() {
    return patientType;
  }

  public void setPatientType(int patientType) {
    this.patientType = patientType;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<RegimenDetail> getRegimenDetail() {
    return regimenDetail;
  }

  public void setRegimenDetail(List<RegimenDetail> regimenDetail) {
    this.regimenDetail = regimenDetail;
  }

  public List<Cancer> getParentCancers() {
    return parentCancers;
  }

  public void setParentCancers(List<Cancer> parentCancers) {
    this.parentCancers = parentCancers;
  }

  public List<Cancer> getSubCancers() {
    return subCancers;
  }

  public void setSubCancers(List<Cancer> subCancers) {
    this.subCancers = subCancers;
  }
}
