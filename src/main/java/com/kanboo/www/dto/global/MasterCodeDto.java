package com.kanboo.www.dto.global;

import com.kanboo.www.domain.entity.global.MasterCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterCodeDto {

    private String masterCodeIdx;
    private String masterCodeKo;
    private String masterCodeEn;

    public MasterCode dtoToEntity() {
        return MasterCode.builder()
                .masterCodeIdx(masterCodeIdx)
                .masterCodeKo(masterCodeKo)
                .masterCodeEn(masterCodeEn)
                .build();
    }
}
