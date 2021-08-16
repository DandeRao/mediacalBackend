package com.patient.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "last_modified_date")
    @Size(max = 10485760)
    private Date lastModifiedDate;

}
