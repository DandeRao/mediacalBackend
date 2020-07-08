package com.patient.models.responses;

import com.patient.models.Brand;
import com.patient.models.LevelType;
import com.patient.models.Reference;
import com.patient.models.Drug;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class Regimen {
  private Integer id;

   private String name;

  private String schedule;

  private String emetogenicPotential;

  private String dosageModifications;

  private List<Reference> references;

  private List<Drug> drugs = new ArrayList<>();

  private List<LevelType> regimenLevels = new ArrayList<>();
}
