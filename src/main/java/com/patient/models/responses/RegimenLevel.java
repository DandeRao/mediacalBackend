package com.patient.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RegimenLevel {
    public Integer regimenLevelId;
    public List<Integer> regimenIds = new ArrayList<>();
}
