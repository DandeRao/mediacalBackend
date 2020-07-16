package com.patient.repos;

import com.patient.models.Drug;
import com.patient.models.DrugBrandLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugBrandLinkRepository extends JpaRepository<DrugBrandLink, Integer> {
  @Query(value = "select MAX(id) from drug_brand_link", nativeQuery = true)
  Integer getMaxId();

  default Integer getNextId() {
    return null == this.getMaxId() ? 1 : this.getMaxId() + 1;
  }

  @Query(value = "select r from DrugBrandLink r where r.drugBrandId = :brandId and r.drugId= :drugId")
  DrugBrandLink getDrugBrandLinkByDrugAndBrandId(@Param("brandId") Integer brandId, @Param("drugId") Integer drugId);
}
