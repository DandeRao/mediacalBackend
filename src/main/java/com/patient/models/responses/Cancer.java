package com.patient.models.responses;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Cancer {
  private Integer id;
  private String title;
  private List<RegimenLevel> regimenByLevelList = new ArrayList<>();
  private List<Cancer> subCancers  = new ArrayList<>();
}
