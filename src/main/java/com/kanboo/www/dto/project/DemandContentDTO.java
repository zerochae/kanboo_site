package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Demand;
import com.kanboo.www.domain.entity.project.DemandContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandContentDTO {

    private Long demandCnIdx;
    private DemandDTO demand;
    private String demandCnNum;
    private String demandCnSe;
    private String demandCnId;
    private String demandCnNm;
    private String demandCnDetail;
    private String demandCnRequstNm;
    private String demandCnRm;

    public DemandContent dtoToEntity() {
        return DemandContent.builder()
                .demandCnIdx(demandCnIdx)
                .demand(demand.dtoToEntity())
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
