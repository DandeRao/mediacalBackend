package com.patient.models.responses;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Cancer {
  private Integer id;
  private String title;
  private List<Regimen> regimenList = new ArrayList<>();
  private List<Cancer> subCancers  = new ArrayList<>();
}
