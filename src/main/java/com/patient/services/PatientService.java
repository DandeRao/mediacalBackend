package com.patient.services;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.Patient;
import com.patient.repos.RegimenDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.repos.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private RegimenDetailRepository regimenDetailRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ObjectMapper objectMapper;


	public List<Patient> getAllPatients(){
		return patientRepository.findAll();
	}

	public long deletePatient(int id) {
		Patient patient = patientRepository.findOne(id);
		patientRepository.delete(patient);
		return id;
	}

	public Patient addOrUpdatePatient(String payLoad) throws JsonParseException, JsonMappingException, IOException {
		Patient patient1 = objectMapper.readValue(payLoad, Patient.class);
        Patient patient = Patient.builder()
                .title(patient1.getTitle())
                .id(patient1.getId() != 0? patient1.getId() : patientRepository.getMaxId()+1)
                .build();
		return patientRepository.save(patient);
	}
}
