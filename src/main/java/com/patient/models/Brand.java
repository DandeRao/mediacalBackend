package com.patient.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "brand")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private int id;

  @Column(name = "brand_name")
  @Size(max = 10485760)
  private String brandName;

  @Column(name = "generic_name")
  @Size(max = 10485760)
  private String genericName;

  @Column(name = "manufacturer")
  @Size(max = 10485760)
  private String manufacturer;

  @ManyToOne()
  @JsonBackReference
  private RegimenDetail regimenDetail;
}