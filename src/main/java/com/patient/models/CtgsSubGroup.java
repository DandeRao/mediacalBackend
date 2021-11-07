package com.patient.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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


    @OneToMany()
    @JoinColumn(name="ctgs_sub_group_id", referencedColumnName = "id")
    private List<CtgsOption> ctgsOptions;
}
