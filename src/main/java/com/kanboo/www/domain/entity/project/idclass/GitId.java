package com.kanboo.www.domain.entity.project.idclass;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class GitId implements Serializable {

    private Long project;
}
