package com.kanboo.www.dto.global;

import com.kanboo.www.domain.entity.global.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoleDto {

    private Long roleIdx;
    private String roleNm;

    public Role dtoToEntity() {
        return Role.builder()
                .roleIdx(roleIdx)
                .roleNm(roleNm)
                .build();
    }
}
