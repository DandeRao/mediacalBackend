package com.patient.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "subcancertype3")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SubCancerType3 {

    @Column(name = "pk")
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "subcancer_type2_id")
    private int subcancertype2id;

    @Column(name = "subcancer_type1_id")
    private int subcancertype1id;


    @Column(name = "cancer_type_id")
    private int cancerTypeId;

    @Column(name = "patient_type_id")
    private int patienttypeid;

    @Column(name = "linked_sub_cancer_id")
    private int linkedSubCancerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubcancertype2id() {
        return subcancertype2id;
    }

    public void setSubcancertype2id(int subcancertype2id) {
        this.subcancertype2id = subcancertype2id;
    }

    public int getSubcancertype1id() {
        return subcancertype1id;
    }

    public void setSubcancertype1id(int subcancertype1id) {
        this.subcancertype1id = subcancertype1id;
    }

    public int getCancerTypeId() {
        return cancerTypeId;
    }

    public void setCancerTypeId(int cancerTypeId) {
        this.cancerTypeId = cancerTypeId;
    }

    public int getPatienttypeid() {
        return patienttypeid;
    }

    public void setPatienttypeid(int patienttypeid) {
        this.patienttypeid = patienttypeid;
    }

    public int getLinkedSubCancerId() {
        return linkedSubCancerId;
    }

    public void setLinkedSubCancerId(int linkedSubCancerId) {
        this.linkedSubCancerId = linkedSubCancerId;
    }
}
