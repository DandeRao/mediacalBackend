package com.patient.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "ctgs_group")
@Data
@Builder
@AllArgsConstructor
public class CtgsGroup {
    @Id
    @Column(name = "id")
    Integer id;

    @Column(name = "title")
    String title;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "cancer_regimen_link",
            joinColumns = {@JoinColumn(name = "cancer_id")},
            inverseJoinColumns = {@JoinColumn(name = "regimen_id")}
    )
    List<CtgsSubGroup> ctgsSubGroupList = new ArrayList<>();
}
