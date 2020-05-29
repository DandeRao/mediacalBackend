package com.patient.services;

import com.patient.models.LevelType;
import com.patient.repos.LevelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@ComponentScan("com.patient.services")
@Service
public class LevelTypeService {

  @Autowired
  LevelTypeRepository levelTypeRepository;

  List<LevelType> getRegimenLevels() {
    List<LevelType> levels = new ArrayList<>();
    levels.addAll(levelTypeRepository.getLevels());
    return levels;
  }

}
