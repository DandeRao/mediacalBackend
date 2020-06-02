package com.patient.repos;

import com.patient.models.Reference;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
  @Query(value = "select MAX(id) from regimen_reference", nativeQuery = true)
  Integer getMaxId();

  @Query("Select r from Reference r where r.id = :id")
  public Reference getById(@Param("id") int id);
}
