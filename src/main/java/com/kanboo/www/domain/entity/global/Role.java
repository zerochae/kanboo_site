package com.kanboo.www.domain.entity.global;

import com.kanboo.www.dto.global.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    private Long roleIdx;
    private String roleNm;

    public RoleDto entityToDto() {
        return RoleDto.builder()
                .roleIdx(roleIdx)
                .roleNm(roleNm)
                .build();
    }
}
