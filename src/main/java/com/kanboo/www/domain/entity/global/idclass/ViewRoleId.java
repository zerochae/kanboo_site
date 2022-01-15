package com.kanboo.www.domain.entity.global.idclass;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ViewRoleId implements Serializable {

    private Long view;
    private Long role;
}
