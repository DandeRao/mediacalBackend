package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "brand_regimen_link")
@Data
@Builder
@AllArgsConstructor
public class BrandRegimenLink {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "brand_id")
  private Integer brandId;

  @Column(name = "regimen_id")
  private Integer regimenId;

  public BrandRegimenLink(int brandId, int regimenId) {
    this.brandId = brandId;
    this.regimenId = regimenId;
  }
}
