package com.kanboo.www.domain.entity.global;

import com.kanboo.www.dto.global.ViewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "view")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class View {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewIdx;

    private String viewNm;
    private String viewURL;

    private ViewDto entityToDto() {
        return ViewDto.builder()
                .viewIdx(viewIdx)
                .viewNm(viewNm)
                .viewURL(viewURL)
                .build();
    }
}
