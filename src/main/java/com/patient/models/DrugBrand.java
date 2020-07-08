package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "drug_brand")
@Data
@Builder
@AllArgsConstructor
public class DrugBrand {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "brand_name")
  private String brandName;

  @Column(name = "manufacturer")
  private String manufacturer;

}
