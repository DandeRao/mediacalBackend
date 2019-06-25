package com.patient.repos;
import com.patient.models.SubCancerType1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCancerTypeRepository  extends JpaRepository<SubCancerType1, Integer> {

    @Query("Select a from SubCancerType1 a where a.subcancertypeid = :id")
    List<SubCancerType1> findSubCancerType1ById(@Param("id") int id);

    @Query(value = "select MAX(pk) from subcancertype1", nativeQuery = true)
    int getMaxId();

}
