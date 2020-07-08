package com.patient.repos;

import com.patient.models.DrugRegimenLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRegimenLinkRepository extends JpaRepository<DrugRegimenLink, Integer> {
  @Query(value = "select MAX(id) from brand_regimen_link", nativeQuery = true)
  Integer getMaxId();

  @Query("Select c from DrugRegimenLink c where c.drugId = :drugId and c.regimenId = :regimenId")
  public DrugRegimenLink findDrugRegimenLinkByBrandIdAndRegimenId(@Param("drugId") int brandId, @Param("regimenId") int regimenId);

  @Query("Select r from DrugRegimenLink r where r.id = :id")
  public DrugRegimenLink getById(@Param("id") int id);
}
