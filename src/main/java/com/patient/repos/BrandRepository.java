package com.patient.repos;

import com.patient.models.Brand;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
  @Query(value = "select MAX(id) from brand", nativeQuery = true)
  Integer getMaxId();

  @Query(value = "SELECT setval(pg_get_serial_sequence('brand', 'id'), MAX(id)) FROM brand", nativeQuery = true)
  Integer resetSequence();

  @Query("Select r from Brand r where r.id = :id")
  public Brand getById(@Param("id") int id);

}
