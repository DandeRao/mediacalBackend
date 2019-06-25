package com.patient.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "subcancertype3")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SubCancerType3 {

    @Column(name = "pk")
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "subcancer_type2_id")
    private int subcancertypeid;

    @ManyToOne
    @JoinColumn(name = "id")
    private SubCancerType2 subCancerType2;
}
