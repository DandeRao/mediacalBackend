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
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "drug_id")
  private Integer drugId;

  @Column(name = "drug_brand_id")
  private Integer drugBrandId;
}
