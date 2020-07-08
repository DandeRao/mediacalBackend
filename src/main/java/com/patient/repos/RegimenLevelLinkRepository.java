package com.patient.repos;

import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenLevelLinkRepository extends JpaRepository<RegimenLevelLink, Integer> {

  @Query(value = "select MAX(id) from regimen_level_link", nativeQuery = true)
  Integer getMaxId();

  default Integer getNextId() {
    return null ==getMaxId() ? 1 : this.getMaxId() + 1;
  }

  @Query("Select r from RegimenLevelLink r where r.levelId = :levelId and r.regimenId = :regimenId")
  public RegimenLevelLink findRegimenLevelLinkByLevelIdAndRegimenId(@Param("levelId") int levelId, @Param("regimenId") int regimenId);

  @Query("Select r from RegimenLevelLink r where r.levelId = :levelId")
  public List<RegimenLevelLink> findRegimenLevelLinkByLevelId(@Param("levelId") int levelId);

//  @Query("delete from RegimenLevelLink r where r.levelId = :levelId")
  public default void deleteRegimenLevelLinksByLevelId(int levelId) {
    delete(findRegimenLevelLinkByLevelId(levelId));
  }

  @Query("Select r from RegimenLevelLink r where r.id = :id")
  public RegimenLevelLink getById(@Param("id") int id);
}
