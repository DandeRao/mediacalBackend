package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "drug_regimen_link")
@Data
@Builder
@AllArgsConstructor
public class DrugRegimenLink {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "drug_id")
  private Integer drugId;

  @Column(name = "regimen_id")
  private Integer regimenId;

  public DrugRegimenLink(int drugId, int regimenId) {
    this.drugId = drugId;
    this.regimenId = regimenId;
  }
}
