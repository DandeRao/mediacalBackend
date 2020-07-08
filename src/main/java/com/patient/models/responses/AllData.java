package com.patient.models.responses;

import com.patient.models.Patient;
import lombok.Getter;
import lombok.Setter;
import com.patient.models.Drug;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AllData {
  private List<PatientType> patients = new ArrayList();
  private List<Regimen> regimen = new ArrayList();
  private List<Drug> drugs = new ArrayList();
}
