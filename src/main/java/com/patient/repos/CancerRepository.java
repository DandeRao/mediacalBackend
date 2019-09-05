package com.patient.repos;

import com.patient.models.Cancer;
import com.patient.models.CancerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CancerRepository extends JpaRepository<Cancer, Integer> {

  @Query("Select a from Cancer a where a.patientType = :id and a.parentId = null")
  List<Cancer> getCancersByPatientId(@Param("id") int id);

  @Query("Select a from Cancer a where a.parentId = :id and a.subCancerType = null")
  List<Cancer> getCancersByParentId(@Param("id") int id);

  @Query("Select a.regimen from Cancer a where a.id = :id")
  String getRegimenByPatientId(@Param("id") int id);


  @Query("Select a from Cancer a where a.parentId = :id and a.subCancerType = 'ADJUVANT'")
  List<Cancer> getAdjuvantCancersByParentId(@Param("id") int id);

  @Query("Select a from Cancer a where a.parentId = :id and a.subCancerType = 'NEOADJUVANT'")
  List<Cancer> getNeoAdjuvantCancersByParentId(@Param("id") int id);

  @Query("Select a from Cancer a where a.parentId = :id and a.subCancerType = 'METASTATIC'")
  List<Cancer> getMetaStaticCancersByParentId(@Param("id") int id);

  @Query("Select a from Cancer a where a.id = :id")
  Cancer getCancerById(@Param("id") int id);

  @Query(value = "select MAX(id) from cancer", nativeQuery = true)
  int getMaxId();
}
