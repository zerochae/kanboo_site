package com.kanboo.www.domain.entity.global;

import com.kanboo.www.dto.global.MasterCodeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterCode {

    @Id
    private String masterCodeIdx;

    private String masterCodeKo;
    private String masterCodeEn;

    public MasterCodeDto entityToDto() {
        return MasterCodeDto.builder()
                .masterCodeIdx(masterCodeIdx)
                .masterCodeKo(masterCodeKo)
                .masterCodeEn(masterCodeEn)
                .build();
    }
}
