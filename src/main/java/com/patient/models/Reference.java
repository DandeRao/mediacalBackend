package com.patient.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "regimen_reference")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reference implements Serializable {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "reference")
  @Size(max = 10485760)
  private String referenceValue;
//
//  @Column(name = "regimen_id")
//  private Integer regimenId;
  @ManyToOne()
  @JsonBackReference
  private RegimenDetail regimenDetail;
}
