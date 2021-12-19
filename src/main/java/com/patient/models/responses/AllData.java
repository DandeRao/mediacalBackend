package com.patient.models.responses;

import com.patient.models.CtgsGroup;
import com.patient.models.LevelType;
import com.patient.models.Patient;
import lombok.Getter;
import lombok.Setter;
import com.patient.models.Drug;

import java.util.*;

@Setter
@Getter
public class AllData {
  private List<PatientTypeV2> patients = new ArrayList();
  private Map<String, Regimen> regimen = new HashMap<>();
  private List<Drug> drugs = new ArrayList();
  private Map<Integer, CtgsGroup> ctgsGroups = new LinkedHashMap<>();
  private Map<Integer, LevelType> regimenLevel = new LinkedHashMap<>();
}
