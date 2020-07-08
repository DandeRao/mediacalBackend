package com.patient.repos;

import com.patient.models.Drug;
import com.patient.models.DrugBrandLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrugBrandLinkRepository extends JpaRepository<DrugBrandLink, Integer> {
  @Query(value = "select MAX(id) from drug_brand_link", nativeQuery = true)
  Integer getMaxId();

  default Integer getNextId() {
    return null ==getMaxId() ? 1 : this.getMaxId() + 1;
  }

}
