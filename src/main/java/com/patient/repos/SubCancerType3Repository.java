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

    @Query("Select a from SubCancerType3 a where a.patienttypeid = :patientId and a.cancerTypeId = :cancerId  and a.subcancertype1id = 0 and a.subcancertype2id = 0  and a.linkedSubCancerId = 0")
    List<SubCancerType3> findSubCancerType1ById(@Param("patientId") int patientId, @Param("cancerId") int cancerId);

    @Query("Select a from SubCancerType3 a where a.patienttypeid = :patientId and a.cancerTypeId = 0  and a.subcancertype1id = 0 and a.subcancertype2id = 0  and a.linkedSubCancerId = 0 and a.cancerTypeId = 0")
    List<SubCancerType3> findCancerById(@Param("patientId") int patientId);


    @Query("Select a from SubCancerType3 a where a.patienttypeid = :pid and a.subcancertype1id = :id and a.linkedSubCancerId = 0  and a.subcancertype2id = 0  and a.cancerTypeId = :cid")
    List<SubCancerType3> findSubCancerType2ById(@Param("id") int id, @Param("pid") int pid, @Param("cid") int cid);

    @Query("Select MAX(id) from SubCancerType3")
    int getMaxId();
}
