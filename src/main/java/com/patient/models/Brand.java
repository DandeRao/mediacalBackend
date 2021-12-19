package com.patient.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "brand")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "brand_name")
  @Size(max = 10485760)
  private String brandName;

  @Column(name = "generic_name")
  @Size(max = 10485760)
  private String genericName;

  @Column(name = "manufacturer")
  @Size(max = 10485760)
  private String manufacturer;

  @Column(name = "last_modified_date")
  private Date lastModifiedDate;
}