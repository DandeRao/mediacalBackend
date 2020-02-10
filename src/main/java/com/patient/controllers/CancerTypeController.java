package com.patient.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.CancerResponse;
import com.patient.services.CancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class CancerTypeController {
//
//    @Autowired
//    private CancerTypeService cancerTypeService;
//
//    @Autowired
//    private SubCancerType3Service subCancerType3Service;

    @Autowired
    private CancerService cancerService;

//    @RequestMapping(value = "/cancerTypeController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    public List<CancerType> getAllCancerTypes() {
//        return cancerTypeService.getAllCancerTypes();
//    }
//
//
//    @RequestMapping(value = "/cancerTypeController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    public CancerType getCancertype(@PathVariable("id") Integer id) {
//        return cancerTypeService.getCancerTypeId(id);
//    }
//
//    @RequestMapping(value = "/cancerTypeControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    public List<CancerType> getCancertypeId(@PathVariable("id") Integer id) {
//        return cancerTypeService.getCancerTypes(id);
//    }
//
//    @RequestMapping(value = "/cancerTypeControllerById/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//    public SubCancerType3 addRegimenDetail(@RequestBody String payLoad)
//            throws JsonParseException, JsonMappingException, IOException {
//        return subCancerType3Service.addOrUpdateCancer(payLoad);
//    }
//
//    @RequestMapping(value = "/cancerTypeControllerById/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
//    public CancerType UpdateRegimenDetail(@RequestBody String payLoad)
//            throws JsonParseException, JsonMappingException, IOException {
//        return cancerTypeService.updateCancerType(payLoad);
//    }
//    /**
//     * This method is used to delete the patient.
//     *
//     * @parampayLoad
//     * @throws JsonParseException
//     * @throws JsonMappingException
//     * @throws IOException
//     */
    @CrossOrigin("*")
    @RequestMapping(value = "/cancerTypeControllerById/{patienttypeid}", produces = MediaType.ALL_VALUE, method = RequestMethod.DELETE)
    public boolean deleteRegimenDetail(@PathVariable("patienttypeid") Integer id) {
        return cancerService.deleteCancerId(id);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/getAllCancerNames", produces = MediaType.ALL_VALUE, method = RequestMethod.GET)
    public CancerResponse getAllCancerNames() {
        return cancerService.getAllCancerNames();
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/getCancersByPatient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerResponse getCancersByPatientId(@PathVariable("patientId") Integer id) {
        return cancerService.getCancersWithPatientId(id);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/getCancersByParentId/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerResponse getCancersByParentId(@PathVariable("parentId") Integer id) {
        return cancerService.getCancersWithParentId(id);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/cancerTypeControllerById/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public CancerResponse addRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return cancerService.addOrUpdateCancer(payLoad);
    }
    @CrossOrigin("*")
        @RequestMapping(value = "/cancerTypeControllerById/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public CancerResponse UpdateRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
            return cancerService.addOrUpdateCancer(payLoad);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/updateRegimenInCancer", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public CancerResponse updateRegimenInCancer(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return cancerService.updateRegimenInCancer(payLoad);
    }
}
