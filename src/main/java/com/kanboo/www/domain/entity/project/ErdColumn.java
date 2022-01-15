package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.ErdColumnDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ErdColumn {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erdColumnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "erd_idx")
    private Erd erd;

    private String erdColumnName;
    private String erdColumnType;
    private String erdColumnConstraint;
    private String erdColumnReferences;

    public ErdColumnDTO entityToDto() {
        return ErdColumnDTO.builder()
                .erdColumnIdx(erdColumnIdx)
                .erdColumnName(erdColumnName)
                .erdColumnType(erdColumnType)
                .erdColumnConstraint(erdColumnConstraint)
                .erdColumnReferences(erdColumnReferences)
                .build();
    }
}
