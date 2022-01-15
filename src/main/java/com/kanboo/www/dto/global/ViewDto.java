package com.kanboo.www.dto.global;

import com.kanboo.www.domain.entity.global.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ViewDto {

    private Long viewIdx;
    private String viewNm;
    private String viewURL;

    public View dtoToEntity() {
        return View.builder()
                .viewIdx(viewIdx)
                .viewNm(viewNm)
                .viewURL(viewURL)
                .build();
    }
}
