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


  List<LevelType> getLevelsByType(String type) {

    List<LevelType> levels = new ArrayList<>();

    if(type != null && !type.equals("")) {
      levels.addAll(levelTypeRepository.findLevelTypeByType(type));
    }

    return levels;
  }
}
