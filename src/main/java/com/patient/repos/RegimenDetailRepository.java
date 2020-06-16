package com.patient.repos;
import com.patient.models.RegimenDetail;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RegimenDetailRepository extends JpaRepository< RegimenDetail, Long> {
    @Query("Select a from RegimenDetail a where a.id = :id")
    RegimenDetail fingRegimenById(@Param("id") int id);

    @Query(value = "select * from regimen_detail ORDER BY disp_name ASC", nativeQuery = true)
    List<RegimenDetail> getAllRegimenDetails();


    @Query(value = "Select a from RegimenDetail a where a.id = :id")
    RegimenDetail getRegimenDetailWithId(@Param("id") int id);

    @Query(value = "Select a from RegimenDetail a where a.id in (:id) ORDER BY disp_name ASC")
    List<RegimenDetail> getRegimenFromListOfIds(@Param("id") List<Integer> id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM RegimenDetail c WHERE  c.id=:id")
    void delteRegimenWithId(@Param("id") int id);

    @Query(value = "select MAX(id) from regimen_detail", nativeQuery = true)
    int getMaxId();

    @Query(value = "select level from level_type where (level_type.id in (select level_id from regimen_level_link where regimen_id in (select regimen_id from cancer_regimen_link where cancer_id = :cancerId))) ORDER BY level ASC", nativeQuery = true)
    List<String> getRegimenLevelNameByCancerId(@Param("cancerId") int id);

    @Query("Select r from RegimenDetail r where r.id = :id")
    public RegimenDetail getById(@Param("id") int id);

    @Query(value = "select * from regimen_detail where id in (select regimen_id from regimen_level_link where level_id in (select id from level_type where level =:regimenType) and regimen_id in (select cancer_regimen_link.regimen_id from cancer_regimen_link where cancer_id =:cancerId)) ORDER BY disp_name ASC", nativeQuery = true)
    public List<RegimenDetail> getByCancerIdAndRegimenLevelType(@Param("cancerId") int id, @Param("regimenType") String regimenType);

    @Query(value = "select * from regimen_detail where id in (select regimen_id from cancer_regimen_link where cancer_id in (select cancer_regimen_link.regimen_id from cancer_regimen_link where cancer_id =:cancerId)) ORDER BY disp_name ASC", nativeQuery = true)
    public List<RegimenDetail> getRegimenDetailByCancerId(@Param("cancerId") int id);

    @Query(value = "select * from regimen_detail where id in (select regimen_id from cancer_regimen_link where cancer_id in (select cancer_regimen_link.regimen_id from cancer_regimen_link where cancer_id != :cancerId)) ORDER BY disp_name ASC", nativeQuery = true)
    public List<RegimenDetail> getRegimenDetailNotLinkedToCancerId(@Param("cancerId") int id);


}
