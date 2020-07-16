package com.patient.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.patient.models.CancerResponse;
import com.patient.models.Drug;
import com.patient.models.LevelType;
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

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<RegimenDetail> getRegimentDetails(){
        return regimenDetailService.getAllRegimenDetails();
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public RegimenDetail getRegimenDetail(@PathVariable("id") Integer id) {
        return regimenDetailService.getRegimenDetailId(id);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/{id}/updateCancerToList/{commaSeperatedCancerIds}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public RegimenDetail updateCancerToRegimenList(@PathVariable("id") Integer id, @PathVariable("commaSeperatedCancerIds") String cancerId) {
        return regimenDetailService.updateCancerToRegimenList(id, cancerId);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/{id}/names", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerResponse getregimenDetailId(@PathVariable("id") int id) {
        return regimenDetailService.getRegimenDetailByCancerId(id);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/{id}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerResponse getregimenDetailIdAndType(@PathVariable("id") int id, @PathVariable("type") String type) {
        return regimenDetailService.getRegimenDetailByCancerIdAndType(String.valueOf(id), type);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/levels/{type}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<LevelType> getLevels(@PathVariable("type") String type) {
        return regimenDetailService.getLevels();
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
    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public RegimenDetail addRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.updateRegimenDetail(payLoad, null);
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
    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/add/level", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public LevelType addLevelType(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.addLevel(payLoad);
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
    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/edit/level", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String editLevelType(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.editLevel(payLoad);
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
    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/delete/level", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String  deleteLevelType(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.deleteLevel(payLoad);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/updateRegimen", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public RegimenDetail UpdateRegimenDetail(@RequestBody String payLoad)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.updateRegimenDetail(payLoad, null);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/updateRegimen/cancerId/{cancerId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public RegimenDetail createOrUpdateRegimenAndAddToCancer(@RequestBody String payLoad, @PathVariable("cancerId") Integer cancerId)
            throws JsonParseException, JsonMappingException, IOException {
        return regimenDetailService.updateRegimenDetail(payLoad, cancerId);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/linkRegimenToCancer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.PUT)
    public String linkRegimenToCancer(@RequestBody String payLoad) {
        try {
            regimenDetailService.linkRegimenToCancer(payLoad);
            return "SUCCESS";
        } catch (Exception e) {
            return "FAILED";
        }
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/deleteRegimenFromCancer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public String deleteRegimenFromCancer(@RequestBody String payLoad){
        try {
            regimenDetailService.unLinkRegimenToCancer(payLoad);
            return "SUCCESS";
        } catch (Exception e) {
            return "FAILED";
        }
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
    @RequestMapping(value = "/regimenDetailController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public int deleteRegimenDetail(@PathVariable("id") int id) {
        regimenDetailService.deleteRegimenDetail(id);
        return id;
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/getRegimenListToAddToCancer/{cancerId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerResponse getRegimenListToAddToCancerWithType(@PathVariable("cancerId") int cancerId, @PathVariable("type") String type) {
        return regimenDetailService.getRegimenListToAddToCancer(cancerId, type);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/getRegimenListToAddToCancer/{cancerId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CancerResponse getRegimenListToAddToCancer(@PathVariable("cancerId") int cancerId) {
        return regimenDetailService.getRegimenListToAddToCancer(cancerId, null);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/getAllDrugs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<Drug> getAllDrugs() {
        return regimenDetailService.getAllDrugs();
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/add/drug", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Drug addDrug(@RequestBody String payLoad) throws IOException {
        return regimenDetailService.addDrug(payLoad);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/regimenDetailController/edit/drug", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Drug editDrug(@RequestBody String payLoad) throws IOException {
        return regimenDetailService.editDrug(payLoad);
    }
}
