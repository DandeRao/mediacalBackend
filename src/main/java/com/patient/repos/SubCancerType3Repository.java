package com.patient.repos;
import com.patient.models.SubCancerType3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCancerType3Repository extends JpaRepository<SubCancerType3, Integer> {
    @Query("Select a from SubCancerType3 a where a.id = :id")
    List<SubCancerType3> findSubCancerType3ById(@Param("id") int id);

    @Query("Select a from SubCancerType3 a where a.subcancertype2id = 'null' and a.subcancertype1id = :id")
    List<SubCancerType3> findSubCancerType1ById(@Param("id") int id);

    @Query("Select a from SubCancerType3 a where a.subcancertype1id=:subcancertype1id and a.subcancertype2id=:subcancertype2id")
    List<SubCancerType3> findSubCancerType2ById(@Param("subcancertype1id") int subcancertype1id, @Param("subcancertype2id") int subcancertype2id);

    @Query(value = "select MAX(pk) from subcancertype3", nativeQuery = true)
    int getMaxId();
}
