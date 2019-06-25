package com.patient.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.RegimenDetail;
import com.patient.services.RegimenDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class RegimenDetailController {

    @Autowired
    private RegimenDetailService regimenDetailService;

    @RequestMapping(value = "/regimenDetailController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<RegimenDetail> getRegimentDetails(){
        return regimenDetailService.getAllRegimenDetails();
    }


    @RequestMapping(value = "/regimenDetailController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public RegimenDetail getRegimenDetail(@PathVariable("id") Integer id) {
        return regimenDetailService.getRegimenDetailId(id);
    }


    @RequestMapping(value = "/regimenDetailController/{id}/names", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<RegimenDetail> getregimenDetailId(@PathVariable("id") Integer id) {
        return regimenDetailService.getRegimenDetailById(id);
    }

    /**
     * This method is used for both update and add.
     *
     * @param payLoad
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/regimenDetailController/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public RegimenDetail addRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.addOrUpdateRegimenDetail(payLoad);
    }

    @RequestMapping(value = "/regimenDetailController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public RegimenDetail UpdateRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.updateRegimenDetail(payLoad);
    }
    /**
     * This method is used to delete the patient.
     *
     * @parampayLoad
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/regimenDetailController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public long deleteRegimenDetail(@PathVariable("id") Long id) {
        regimenDetailService.deleteRegimenDetail(id);
        return id;
    }
}
