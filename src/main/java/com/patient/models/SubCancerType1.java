package com.patient.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
    @Table(name = "subcancertype1")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCancerType1 implements Serializable {
    @Column(name = "pk")
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "cancer_type_id")
    private int subcancertypeid;

    @ManyToOne
    @JoinColumn(name = "id")
    private CancerType cancerType;
}
