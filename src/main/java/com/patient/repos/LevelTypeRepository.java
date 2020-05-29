package com.patient.repos;

import com.patient.models.LevelType;
import com.patient.models.RegimenDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LevelTypeRepository extends JpaRepository<LevelType, Integer> {

  @Query("Select a from LevelType a where a.level = :id")
  LevelType findLevelTypeByLevelName(@Param("id") String type);

  @Query(value = "select MAX(pk) from level_type", nativeQuery = true)
  int getMaxId();

  @Query("Select a from LevelType a")
  List<LevelType> getLevels();
}
