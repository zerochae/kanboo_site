package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.DemandContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demand_content")
@Builder
public class DemandContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandCnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_idx")
    private Demand demand;

    private String demandCnNum;
    private String demandCnSe;
    private String demandCnId;
    private String demandCnNm;
    private String demandCnDetail;
    private String demandCnRequstNm;
    private String demandCnRm;

    public DemandContentDTO entityToDto() {
        return DemandContentDTO.builder()
                .demandCnIdx(demandCnIdx)
                .demand(demand.entityToDto())
                .demandCnNum(demandCnNum)
                .demandCnSe(demandCnSe)
                .demandCnId(demandCnId)
                .demandCnNm(demandCnNm)
                .demandCnDetail(demandCnDetail)
                .demandCnRequstNm(demandCnRequstNm)
                .demandCnRm(demandCnRm)
                .build();
    }
}
