package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.ErdColumnDTO;
import com.kanboo.www.dto.project.ErdDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Erd {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erdIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;
    private String erdName;
    private int erdOrder;

    @OneToMany(mappedBy = "erd")
    private List<ErdColumn> columns = new ArrayList<>();

    public ErdDTO entityToDto() {
        ErdDTO erdDTO = ErdDTO.builder()
                .erdIdx(erdIdx)
                .project(project.entityToDto())
                .erdName(erdName)
                .erdOrder(erdOrder)
                .build();

        if(columns != null && !columns.isEmpty()) {
            List<ErdColumnDTO> list = new ArrayList<>();
            columns.forEach(item -> {
                ErdColumnDTO erdColumnDTO = item.entityToDto();
                erdColumnDTO.setErd(erdDTO);
                list.add(erdColumnDTO);
            });

            erdDTO.setColumns(list);
        } else {
            erdDTO.setColumns(new ArrayList<>());
        }
        return erdDTO;
    }
}
