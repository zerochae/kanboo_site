package com.kanboo.www.dto.global;

import com.kanboo.www.domain.entity.global.ViewRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ViewRoleDto {

    private ViewDto view;
    private RoleDto role;

    public ViewRole dtoToEntity() {
        return ViewRole.builder()
                .view(view.dtoToEntity())
                .role(role.dtoToEntity())
                .build();
    }
}
