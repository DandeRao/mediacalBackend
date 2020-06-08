package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "cancer_regimen_link")
@Data
@Builder
@AllArgsConstructor
public class CancerRegimenLink  implements Serializable {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "cancer_id")
  private Integer cancerId;

  @Column(name = "regimen_id")
  private Integer regimenId;

  public CancerRegimenLink(Integer cancerId, Integer regimenId) {
    this.cancerId = cancerId;
    this.regimenId = regimenId;
  }
}
