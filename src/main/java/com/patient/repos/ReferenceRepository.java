package com.patient.repos;

import com.patient.models.Reference;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
  @Query(value = "select MAX(id) from regimen_reference", nativeQuery = true)
  Integer getMaxId();

  @Query("Select r from Reference r where r.id = :id")
  public Reference getById(@Param("id") int id);

  @Query(value =  "Select * from regimen_reference where regimen_detail_id = :id", nativeQuery = true)
  public List<Reference> getByRegimenId(@Param("id") int id);
}
