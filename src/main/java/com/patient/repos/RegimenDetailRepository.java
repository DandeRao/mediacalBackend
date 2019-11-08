package com.patient.repos;
import com.patient.models.RegimenDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenDetailRepository extends JpaRepository< RegimenDetail, Long> {

    @Query("Select a from RegimenDetail a where a.subCancerTypeId3 = :id")
    List<RegimenDetail> findRegimenDetailByCancerId(@Param("id") int id);

    @Query("Select a from RegimenDetail a where a.id = :id")
    RegimenDetail fingRegimenById(@Param("id") int id);

    @Query("Select a from RegimenDetail a where a.subCancerTypeId3 = :id and a.regimenType = :type")
    List<RegimenDetail> findRegimenDetailByIdAndType(@Param("id") int id, @Param("type") String type);

    @Query(value = "select * from regimen_detail", nativeQuery = true)
    List<RegimenDetail> getAllRegimenDetails();


    @Query(value = "Select a from RegimenDetail a where a.id = :id")
    RegimenDetail getRegimenDetailWithId(@Param("id") int id);

    @Query(value = "select MAX(pk) from regimen_detail", nativeQuery = true)
    int getMaxId();

}
