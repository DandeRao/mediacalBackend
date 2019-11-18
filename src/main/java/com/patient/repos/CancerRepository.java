package com.patient.repos;

import com.patient.models.Cancer;
import com.patient.models.CancerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CancerRepository extends JpaRepository<Cancer, Integer> {

  @Query("Select a from Cancer a where a.patientType = :id and a.parentId = null ORDER BY a.title ASC")
  List<Cancer> getCancersByPatientId(@Param("id") int id);

  @Query("Select a from Cancer a where a.parentId = :id and a.subCancerType = null ORDER BY a.title ASC")
  List<Cancer> getCancersByParentId(@Param("id") int id);

  @Query("Select a from Cancer a where a.id = :id")
  Cancer getCancerById(@Param("id") int id);

  @Query(value = "Select id, title from Cancer ORDER BY title ASC", nativeQuery = true)
  List<Object> getAllCancerNames();

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM Cancer c WHERE  c.parentId=:id")
  void deleteCancersWithParentId(@Param("id") int id);

  @Query(value = "select MAX(id) from cancer", nativeQuery = true)
  int getMaxId();
}
