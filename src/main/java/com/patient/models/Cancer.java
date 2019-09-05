package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "cancer")
@Data
@Builder
@AllArgsConstructor
public class Cancer implements Serializable {
  @Column(name = "id")
  @Id
  private int id;

  @Column(name = "patient_type")
  private int patientType;

  @Column(name = "parent_id",
          columnDefinition = "integer default 0")
  private Integer parentId;

  @Column(name = "title")
  private String title;


  @Column(name = "sub_cancer_type")
  private String subCancerType;


  @Column(name = "regimen", length = 10000)
  private String regimen;

  @Transient
  private List<RegimenDetail> regimenDetails;


  @Transient
  private List<Cancer> neoAdjuvantTypes;


  @Transient
  private List<Cancer> adjuvantTypes;


  @Transient
  private List<Cancer> metaStaticTypes;

}
