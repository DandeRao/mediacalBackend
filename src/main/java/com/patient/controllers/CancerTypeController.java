package com.patient.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.CancerType;
import com.patient.services.CancerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class CancerTypeController {

    @Autowired
    private CancerTypeService cancerTypeService;

    @RequestMapping(value = "/cancerTypeController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<CancerType> getAllCancerTypes() {
        return cancerTypeService.getAllCancerTypes();
    }


    @RequestMapping(value = "/cancerTypeController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerType getCancertype(@PathVariable("id") Integer id) {
        return cancerTypeService.getCancerTypeId(id);
    }

    @RequestMapping(value = "/cancerTypeControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<CancerType> getCancertypeId(@PathVariable("id") Integer id) {
        return cancerTypeService.getCancerTypes(id);
    }

    @RequestMapping(value = "/cancerTypeControllerById/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public CancerType addRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return cancerTypeService.addOrUpdateCancerType(payLoad);
    }

    @RequestMapping(value = "/cancerTypeControllerById/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public CancerType UpdateRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return cancerTypeService.updateCancerType(payLoad);
    }
    /**
     * This method is used to delete the patient.
     *
     * @parampayLoad
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/cancerTypeControllerById/{patienttypeid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public long deleteRegimenDetail(@PathVariable("patienttypeid") Integer id) {
        cancerTypeService.deleteCancerType(id);
        return id;
    }
}
