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
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private int id;

  @Column(name = "brand_id")
  private Integer brandId;

  @Column(name = "regimen_id")
  private Integer regimenId;
}
