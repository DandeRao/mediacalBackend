package com.patient.repos;

import com.patient.models.Brand;
import com.patient.models.CancerType;
import com.patient.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Integer> {
  @Query(value = "select MAX(id) from drug", nativeQuery = true)
  Integer getMaxId();

  default Integer getNextId() {
    return null ==getMaxId() ? 1 : this.getMaxId() + 1;
  }


  @Query("Select r from Drug r where r.id = :id")
  public Drug getById(@Param("id") int id);
}
