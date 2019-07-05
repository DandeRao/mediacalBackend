package com.patient.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subcancertype2")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SubCancerType2 implements Serializable {
    @Column(name = "pk")
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "subcancer_type1_id")
    private int subcancertypeid;


    @Column(name = "cancer_type_id")
    private int cancerTypeId;

    @Column(name = "patient_type_id")
    private int patienttypeid;

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

    public int getSubcancertypeid() {
        return subcancertypeid;
    }

    public void setSubcancertypeid(int subcancertypeid) {
        this.subcancertypeid = subcancertypeid;
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
}
