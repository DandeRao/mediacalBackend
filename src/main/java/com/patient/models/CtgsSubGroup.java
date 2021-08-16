package com.patient.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "ctgs_sub_group")
@Data
@Builder
@AllArgsConstructor
public class CtgsSubGroup {
    @Id
    @Column(name = "id")
    Integer id;

    @Column(name = "title")
    String title;


    @ManyToOne()
    @JsonBackReference
    private CtgsOption ctgsOption;

    @ManyToOne()
    @JsonBackReference
    private CtgsGroup ctgsGroup;
}
