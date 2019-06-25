package com.patient.repos;
import com.patient.models.RegimenDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenDetailRepository extends JpaRepository< RegimenDetail, Long> {

    @Query("Select a from RegimenDetail a where a.SubCancerTypeId3 = :id")
    List<RegimenDetail> findRegimenDetailById(@Param("id") int id);

    @Query(value = "select MAX(pk) from regimen_detail", nativeQuery = true)
    int getMaxId();

}
