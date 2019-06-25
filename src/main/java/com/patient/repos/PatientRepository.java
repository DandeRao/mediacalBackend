package com.patient.repos;
import com.patient.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("Select title from Patient")
    List<String>  getAllPatientTypeNames();

    @Query(value = "select MAX(pk) from patient_type", nativeQuery = true)
    int getMaxId();
	
}