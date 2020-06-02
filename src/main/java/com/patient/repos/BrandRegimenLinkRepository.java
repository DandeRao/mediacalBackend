package com.patient.repos;

import com.patient.models.BrandRegimenLink;
import com.patient.models.CancerRegimenLink;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRegimenLinkRepository extends JpaRepository<BrandRegimenLink, Integer> {
  @Query(value = "select MAX(id) from brand_regimen_link", nativeQuery = true)
  Integer getMaxId();

  @Query("Select c from BrandRegimenLink c where c.brandId = :brandId and c.regimenId = :regimenId")
  public BrandRegimenLink findBrandRegimenLinkByBrandIdAndRegimenId(@Param("brandId") int brandId, @Param("regimenId") int regimenId);

  @Query("Select r from BrandRegimenLink r where r.id = :id")
  public BrandRegimenLink getById(@Param("id") int id);
}
