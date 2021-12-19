package com.patient.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PatientTypeV2 {
  String type;
  List<CancerV2> cancers = new ArrayList<>();
}
