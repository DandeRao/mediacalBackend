package com.patient.repos;

import com.patient.models.DrugBrand;
import com.patient.models.DrugBrandLink;
import com.patient.models.DrugRegimenLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugBrandRepository extends JpaRepository<DrugBrand, Integer> {
  @Query(value = "select MAX(id) from drug_brand", nativeQuery = true)
  Integer getMaxId();

  default Integer getNextId() {
    return null ==getMaxId() ? 1 : this.getMaxId() + 1;
  }


  @Query("Select r from DrugBrand r where r.id = :id")
  public DrugBrand getById(@Param("id") int id);

  @Query(value = "select r from DrugBrand r where r.brand_name = :brandName", nativeQuery = true)
  DrugBrandLink getDrugBrandLinkByBrandName(@Param("brandName") String brandName);
}
