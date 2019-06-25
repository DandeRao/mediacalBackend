package com.patient.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.SubCancerType1;
import com.patient.services.SubCancerType1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SubCancerType1Controller {

    @Autowired
    private SubCancerType1Service subCancerType1Service;

    @RequestMapping(value = "/subCancerType1ControllerById", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<SubCancerType1> getSubCancerTypes(){
        return subCancerType1Service.getAllSubCancerTypes();
    }

    @RequestMapping(value = "/subCancerType1Controller/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public SubCancerType1 getSubCancerType(@PathVariable("id") Integer id) {
        return subCancerType1Service.getSubCancerTypeId(id);
    }


    @RequestMapping(value = "/subCancerType1ControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<SubCancerType1> getSubCancerType1ControllerId(@PathVariable("id") Integer id) {
        return subCancerType1Service.getSubCancerType1TypesById(id);
    }

    @RequestMapping(value = "/subCancerType1ControllerById/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public SubCancerType1 addSubCancerType1(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return subCancerType1Service.addOrUpdateSubCancerType1(payLoad);
    }

    @RequestMapping(value = "/subCancerType1ControllerById/edit", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public SubCancerType1 UpdateSubCancerType1(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return subCancerType1Service.addOrUpdateSubCancerType1(payLoad);
    }
    /**
     * This method is used to delete the subCancerType1.
     *
     * @parampayLoad
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/subCancerType1Controller/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public long deleteSubCancerType1(@PathVariable("id") int id) {
        subCancerType1Service.deleteSubCancerType1(id);
        return id;
    }

}
