package com.kanboo.www.domain.entity.global;

import com.kanboo.www.domain.entity.global.idclass.ViewRoleId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "view_role")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ViewRoleId.class)
public class ViewRole {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewIdx")
    private View view;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleIdx")
    private Role role;
}
