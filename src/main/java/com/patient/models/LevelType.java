package com.patient.models;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "level_type")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LevelType implements Serializable {
  @Id
  @Column(name = "pk")
  private int id;


  @Column(name = "level")
  private String level;


  @Column(name = "type")
  private String type;

}
