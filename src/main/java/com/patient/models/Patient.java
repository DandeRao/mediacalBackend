package com.patient.models;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "patient_type")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Patient implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;


    @Column(name = "title")
    private String title;

    @Column(name = "last_modified_date")
    @Size(max = 10485760)
    private Date lastModifiedDate;

}
