package com.patient.controllers;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.SubCancerType2;
import com.patient.services.SubCancerType2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SubCancerType2Controller {

    @Autowired
    private SubCancerType2Service subCancerType2Service;

    @RequestMapping(value = "/subCancerType2ControllerById", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<SubCancerType2> getSubCancerType1Controller() {
        return subCancerType2Service.getAllSubCancerType2();
    }


    @RequestMapping(value = "/subCancerType2ControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<SubCancerType2> getSubCancerType1ControllerId(@PathVariable("id") Integer id) {
        return subCancerType2Service.getSubCancerType2TypesById(id);
    }

    @RequestMapping(value = "/subCancerType2ControllerById/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public SubCancerType2 addSubCancerType1(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return subCancerType2Service.addOrUpdateSubCancerType2(payLoad);
    }

    @RequestMapping(value = "/subCancerType2ControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public SubCancerType2 UpdateSubCancerType1(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return subCancerType2Service.addOrUpdateSubCancerType2(payLoad);
    }
    /**
     * This method is used to delete the subCancerType1.
     *
     * @parampayLoad
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/subCancerType2ControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public long deleteSubCancerType1(@PathVariable("id") int id) {
        subCancerType2Service.deleteSubCancerType2(id);
        return id;
    }

}
