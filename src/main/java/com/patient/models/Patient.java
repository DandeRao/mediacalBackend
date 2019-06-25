package com.patient.models;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "patient_type")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Patient implements Serializable {
    @Id
    @Column(name = "pk")
    private int id;


    @Column(name = "title")
    private String title;

}
