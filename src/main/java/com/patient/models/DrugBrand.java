package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Table(name = "drug_brand")
@Data
@Builder
@AllArgsConstructor
public class DrugBrand {
  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "brand_name")
  private String brandName;

  @Column(name = "manufacturer")
  private String manufacturer;


  @Column(name = "additional_details")
  @Size(max = 10485760)
  String additionalDetails;

  @UpdateTimestamp
  @Column(name = "last_modified_date")
  private java.sql.Date lastModifiedDate;
}
