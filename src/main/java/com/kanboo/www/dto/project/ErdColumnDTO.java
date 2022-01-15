package com.kanboo.www.dto.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kanboo.www.domain.entity.project.Erd;
import com.kanboo.www.domain.entity.project.ErdColumn;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"erd"})
public class ErdColumnDTO {

    private Long erdColumnIdx;

    @JsonBackReference
    private ErdDTO erd;

    private String erdColumnName;
    private String erdColumnType;
    private String erdColumnConstraint;
    private String erdColumnReferences;

    public ErdColumn dtoToEntity() {
        return ErdColumn.builder()
                .erdColumnIdx(erdColumnIdx)
                .erd(erd.dtoToEntity())
                .erdColumnName(erdColumnName)
                .erdColumnType(erdColumnType)
                .erdColumnConstraint(erdColumnConstraint)
                .erdColumnReferences(erdColumnReferences)
                .build();
    }
}
