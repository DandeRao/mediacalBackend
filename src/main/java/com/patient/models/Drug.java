package com.patient.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "drug")
@Data
@Builder
@AllArgsConstructor
public class Drug {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "generic_name")
  private String genericName;


  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
          name = "drug_brand_link",
          joinColumns = {@JoinColumn(name = "drug_id")},
          inverseJoinColumns = {@JoinColumn(name = "drug_brand_id")}
  )
  private List<DrugBrand> drugBrandList = new ArrayList<>();

}
