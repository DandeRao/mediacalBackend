package com.patient.repos;

import com.patient.models.LevelType;
import com.patient.models.RegimenDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelTypeRepository extends JpaRepository<LevelType, Integer> {

  @Query("Select a from LevelType a where a.type = :type ORDER BY a.level ASC")
  List<LevelType> findLevelTypeByType(@Param("type") String type);


  @Query("Select a from LevelType a where a.level = :id")
  LevelType findLevelTypeByLevelName(@Param("id") String type);


  @Query("Select a from LevelType a where a.level = :level and a.type=:type")
  LevelType findLevelTypeByLevelNameAndType(@Param("level") String level, @Param("type") String type);

  @Query(value = "select MAX(pk) from level_type", nativeQuery = true)
  int getMaxId();

}
