package com.patient.repos;

import com.patient.models.CancerRegimenLink;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CancerRegimenLinkRepository extends JpaRepository<CancerRegimenLink, Integer> {


  @Query(value = "select MAX(id) from cancer_regimen_link", nativeQuery = true)
  public int getMaxId();

  @Query("Select c from CancerRegimenLink c where c.cancerId = :cancerId")
  public List<CancerRegimenLink> findRegimenDetailByCancerId(@Param("cancerId") int cancerId);


  @Query("Select c from CancerRegimenLink c where c.regimenId = :regimenId")
  public List<CancerRegimenLink> getCancersForRegimen(@Param("regimenId") int regimenId);

  @Query("Select r from CancerRegimenLink r where r.id = :id")
  public CancerRegimenLink getById(@Param("id") int id);


  @Query("Select r from CancerRegimenLink r where r.regimenId = :regimenId and r.cancerId = :cancerId")
  public CancerRegimenLink getCancerRegimenLinkByRegimenAndCancerId(@Param("regimenId") int regimenId, @Param("cancerId") int cancerId);

}
