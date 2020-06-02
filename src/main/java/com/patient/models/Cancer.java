package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "cancer")
@Data
@Builder
@AllArgsConstructor
public class Cancer implements Serializable {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

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

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
          name = "cancer_regimen_link",
          joinColumns = {@JoinColumn(name = "cancer_id")},
          inverseJoinColumns = {@JoinColumn(name = "regimen_id")}
  )
  private List<com.patient.models.RegimenDetail> regimenList = new ArrayList<>();
}
