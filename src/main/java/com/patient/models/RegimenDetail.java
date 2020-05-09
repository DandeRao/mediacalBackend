package com.patient.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regimen_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegimenDetail implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "subcancer_type3_id")
    @Size(max = 10485760)
    private String subCancerTypeId3;

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

    @Column(name = "regimen_type")
    private String regimenType;

    @Column(name = "brand_names")
    @Size(max = 10485760)
    private String brandNames;

    @OneToMany(mappedBy = "regimenDetail")
    private List<Reference> references = new ArrayList<>();

    @OneToMany(mappedBy = "regimenDetail")
    private List<Brand> brands = new ArrayList<>();
}
