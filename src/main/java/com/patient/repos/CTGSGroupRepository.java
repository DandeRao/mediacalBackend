package com.patient.repos;

import com.patient.models.CancerType;
import com.patient.models.CtgsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CTGSGroupRepository extends JpaRepository<CtgsGroup, Integer> {
}
