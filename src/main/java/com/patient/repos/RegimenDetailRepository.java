package com.patient.repos;
import com.patient.models.RegimenDetail;
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

    @Query(value = "Select a from RegimenDetail a where a.id in (:id)")
    List<RegimenDetail> getRegimenFromListOfIds(@Param("id") List<Integer> id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM RegimenDetail c WHERE  c.id=:id")
    void delteRegimenWithId(@Param("id") int id);

    @Query(value = "select MAX(pk) from regimen_detail", nativeQuery = true)
    int getMaxId();

}
