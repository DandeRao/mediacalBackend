package com.patient.models.responses;

import com.patient.models.CancerResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PatientType {
  String type;
  List<Cancer> cancers = new ArrayList<>();
}
