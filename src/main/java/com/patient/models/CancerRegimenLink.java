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

  @Column(name = "id")
  @Id
  private int id;

  @Column(name = "cancer_id")
  private Integer cancerId;

  @Column(name = "regimen_id")
  private Integer regimenId;
}
