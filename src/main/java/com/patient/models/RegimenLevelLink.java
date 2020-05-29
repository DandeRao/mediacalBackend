package com.patient.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "regimen_level_link")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegimenLevelLink {
  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private int id;

  @Column(name = "level_id")
  private Integer level_id;

  @Column(name = "regimen_id")
  private Integer regimenId;
}
