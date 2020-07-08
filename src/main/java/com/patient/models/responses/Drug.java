package com.patient.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Drug {
  Integer id;
  String genericName;
  List<DrugBrand> brand = new ArrayList<>();
}
