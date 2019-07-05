package com.patient.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.SubCancerType3;
import com.patient.services.SubCancerType3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SubCancerType3Controller {

    @Autowired
    private SubCancerType3Service subCancerType3Service;

    @RequestMapping(value = "/subCancerType3ControllerById/get", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public List<SubCancerType3> getSubCancerType3Controller(@RequestBody String payLoad) throws JsonParseException, JsonMappingException, IOException {
        return subCancerType3Service.getSubCancerType3TypesById(payLoad);
    }


    @RequestMapping(value = "/subCancerType3ControllerById/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public SubCancerType3 addSubCancerType3(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return subCancerType3Service.addOrUpdateSubCancerType3(payLoad);
    }

    @RequestMapping(value = "/subCancerType3ControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public SubCancerType3 UpdateSubCancerType3(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return subCancerType3Service.addOrUpdateSubCancerType3(payLoad);
    }
    /**
     * This method is used to delete the subCancerType1.
     *
     * @parampayLoad
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/subCancerType3ControllerById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public long deleteSubCancerType3(@PathVariable("id") int id) {
        subCancerType3Service.deleteSubCancerType3(id);
        return id;
    }
}
