package com.patient.repos;

import com.patient.models.LevelType;
import com.patient.models.RegimenDetail;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

@Repository
public interface LevelTypeRepository extends JpaRepository<LevelType, Integer> {

  @Query("Select a from LevelType a where a.level = :id")
  LevelType findLevelTypeByLevelName(@Param("id") String type);

  @Query(value = "select MAX(id) from level_type", nativeQuery = true)
  int getMaxId();

  @Query("Select r from LevelType r where r.id = :id")
  public LevelType getById(@Param("id") int id);

  @Query("Select a from LevelType a")
  List<LevelType> getLevels();
}
