package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "cancer_regimen_link")
@Data
@Builder
@AllArgsConstructor
public class CancerRegimenLink  implements Serializable {

  @Column(name = "id")
  @Id
  private int id;

  @Column(name = "cancer_id")
  private Integer cancerId;

  @Column(name = "regimen_id")
  private Integer regimenId;
}
