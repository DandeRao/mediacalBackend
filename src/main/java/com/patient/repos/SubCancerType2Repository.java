package com.patient.repos;
import com.patient.models.SubCancerType2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCancerType2Repository  extends JpaRepository<SubCancerType2, Integer> {

    @Query("Select a from SubCancerType2 a where a.subcancertypeid = :id")
    List<SubCancerType2> findSubCancerType2ById(@Param("id") int id);

    @Query(value = "select MAX(pk) from subcancertype2", nativeQuery = true)
    long getMaxId();

}
