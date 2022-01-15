package com.kanboo.www.dto.global;

import com.kanboo.www.domain.entity.global.CodeDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetailDto {

    private String codeDetailIdx;
    private MasterCodeDto masterCode;
    private String codeDetailKo;
    private String codeDetailEn;

    public CodeDetail dtoToEntity() {
        return CodeDetail.builder()
                .codeDetailIdx(codeDetailIdx)
                .masterCode(masterCode.dtoToEntity())
                .codeDetailKo(codeDetailKo)
                .codeDetailEn(codeDetailEn)
                .build();
    }
}
