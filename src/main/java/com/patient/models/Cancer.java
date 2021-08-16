package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

  @Transient
  private List<String> regimenLevelsInCancer = new ArrayList<>( );

  @Column(name = "last_modified_date")
  @Size(max = 10485760)
  private Date lastModifiedDate;
}
