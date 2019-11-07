package com.patient.models;

import com.sun.istack.internal.Nullable;
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
    private int id;

    @Nullable
    @Column(name = "subcancer_type3_id")
    private Integer subCancerTypeId3;

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

    @Nullable
    @Column(name = "regimen_type")
    private String regimenType;

    @Column(name = "brand_names")
    @Size(max = 10485760)
    private String brandNames;

}
