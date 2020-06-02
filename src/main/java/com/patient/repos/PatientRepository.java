package com.patient.repos;
import com.patient.models.Patient;
import com.patient.models.RegimenLevelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("Select title from Patient")
    List<String>  getAllPatientTypeNames();


    @Query("Select a.title from Patient a where a.id = :id ORDER BY a.title ASC")
    String getPatientTitileById(@Param("id") int id);


    @Query("Select r from Patient r where r.id = :id")
    public Patient getById(@Param("id") int id);

    @Query(value = "select MAX(pk) from patient_type", nativeQuery = true)
    int getMaxId();
	
}