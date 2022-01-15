package com.kanboo.www.dto.member.nativedto;

import com.kanboo.www.dto.global.RoleDto;
import com.kanboo.www.dto.member.BanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberNative {

    private Long memIdx;
    private String memId;
    private String memNick;
    private String memCelNum;
    private String memToken;
    private String memTag;
    private String memImg;
    private String memPass;
}
