package com.patient.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "regimen_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegimenDetail implements Serializable {
    @Id
    @Column(name = "pk")
    private Long id;


    @Column(name = "subcancer_type3_id")
    private int SubCancerTypeId3;

    @Column(name = "disp_name")
    @Size(max = 10485760)
    private String dispName;

    @Column(name = "name")
    @Size(max = 10485760)
    private String name;

    @Column(name = "schedule")
    @Size(max = 10485760)
    private String schedule;

    @Column(name = "emetogenic_potential")
    @Size(max = 10485760)
    private String emetogenicPotential;

    @Column(name = "reference")
    @Size(max = 10485760)
    private String reference;

    @Column(name = "dosage_modifications")
    @Size(max = 10485760)
    private String dosageModifications;

    @Column(name = "brand_names")
    @Size(max = 10485760)
    private String brandNames;

    @ManyToOne
    @JoinColumn(name = "id")
    private SubCancerType3 subCancerType3;

    @ManyToOne
    @JoinColumn(name = "subcancertype2_id")
    private SubCancerType2 subCancerType2;

    @ManyToOne
    @JoinColumn(name = "subcancertype3_id")
    private SubCancerType1 subCancerType1;

    @Transient
    private int id2;

    @Transient
    private int id3;

    @Transient
    private int id4;


}
