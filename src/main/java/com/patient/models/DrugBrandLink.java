package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "drug_brand_link")
@Data
@Builder
@AllArgsConstructor
public class DrugBrandLink implements Serializable {

  @Id
  @Column(name = "id")
  Integer id;

  @Column(name = "drug_id")
  Integer drugId;

  @Column(name = "drug_brand_id")
  Integer drugBrandId;

  public DrugBrandLink(Integer drugId, Integer drugBrandId) {
    this.drugId = drugId;
    this.drugBrandId = drugBrandId;
    this.id = null;
  }
}
