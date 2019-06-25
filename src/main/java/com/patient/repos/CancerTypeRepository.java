package com.patient.repos;
import com.patient.models.CancerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancerTypeRepository extends JpaRepository<CancerType, Integer> {

    @Query("Select a from CancerType a where a.patienttypeid = :id")
    List<CancerType> findByCancerTypeId(@Param("id") int id);

    @Query(value = "select MAX(pk) from cancer_type", nativeQuery = true)
    int getMaxId();
}
