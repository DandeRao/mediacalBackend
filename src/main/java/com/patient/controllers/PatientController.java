package com.patient.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.CancerResponse;
import com.patient.models.Patient;
import com.patient.models.responses.AllData;
import com.patient.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import com.patient.services.PatientService;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;
	@CrossOrigin("*")
	@RequestMapping(value = "/patientController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Patient> getAllMedicines() {
		return patientService.getAllPatients();
	}
	@CrossOrigin("*")
	@RequestMapping(value = "/patientController/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Patient addRegimenDetail(@RequestBody String payLoad)
			throws JsonParseException, JsonMappingException, IOException {
		return patientService.addOrUpdatePatient(payLoad);
	}
	@CrossOrigin("*")
	@RequestMapping(value = "/patientController/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Patient UpdateRegimenDetail(@RequestBody String payLoad)
			throws JsonParseException, JsonMappingException, IOException {
		return patientService.addOrUpdatePatient(payLoad);
	}

	/**
	 * This method is used to delete the patient.
	 *
	 * @parampayLoad
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@CrossOrigin("*")
	@RequestMapping(value = "/patientController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public int deleteRegimenDetail(@PathVariable("id") Integer id) {
		patientService.deletePatient(id);
		return id;
	}

	/**
	 * This method is used to delete the patient.
	 *
	 * @parampayLoad
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@CrossOrigin("*")
	@RequestMapping(value = "/patientController/getAllData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public AllData getAllData() {
		return patientService.getAllData();
	}
}
