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

}
