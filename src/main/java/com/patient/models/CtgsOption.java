package com.patient.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "ctgs_option")
@Data
@Builder
@AllArgsConstructor
public class CtgsOption {
    @Id
    @Column(name = "id")
    Integer id;
    
    @Column(name = "title")
    String title;

    @Column(name = "grade")
    String grade;
}
