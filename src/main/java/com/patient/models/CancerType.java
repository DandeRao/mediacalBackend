package com.patient.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "cancer_type")
@Data
@Builder
@AllArgsConstructor
public class CancerType implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "patient_type_id")
    private Integer patienttypeid;
//
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Patient patient;

    @Column(name = "title")
    private String title;

}
