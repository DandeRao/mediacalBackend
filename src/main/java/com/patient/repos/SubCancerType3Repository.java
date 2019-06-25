package com.patient.repos;
import com.patient.models.SubCancerType3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCancerType3Repository extends JpaRepository<SubCancerType3, Integer> {
    @Query("Select a from SubCancerType3 a where a.subcancertypeid = :id")
    List<SubCancerType3> findSubCancerType3ById(@Param("id") int id);

    @Query(value = "select MAX(pk) from subcancertype3", nativeQuery = true)
    int getMaxId();
}
