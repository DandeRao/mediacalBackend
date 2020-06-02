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
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "level_id")
  private Integer levelId;

  @Column(name = "regimen_id")
  private Integer regimenId;

  public RegimenLevelLink(int levelId, int regimenId) {
    this.levelId = levelId;
    this.regimenId = regimenId;
  }
}
