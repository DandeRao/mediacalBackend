package com.patient.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "drug")
@Data
@Builder
@AllArgsConstructor
public class Drug implements Serializable {
  @Id
  @Column(name = "id")
  Integer id;

  @Column(name = "generic_name")
  String genericName;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
          name = "drug_brand_link",
          joinColumns = {@JoinColumn(name = "drug_id")},
          inverseJoinColumns = {@JoinColumn(name = "drug_brand_id")}
  )
  List<DrugBrand> drugBrandList = new ArrayList<>();

}
