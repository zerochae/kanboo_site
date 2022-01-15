package com.kanboo.www.dto.member;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.global.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long memIdx;
    private String memId;
    private String memNick;
    private String memCelNum;
    private String memToken;
    private String memTag;
    private String memImg;
    private String memPass;
    private RoleDto role;
    private BanDTO ban;

    public void changeMemberIdx(long memberIdx){
        this.memIdx = memberIdx;
    }

    public Member dtoToEntity() {
        return Member.builder()
                .memIdx(memIdx)
                .memId(memId)
                .memNick(memNick)
                .memCelNum(memCelNum)
                .memToken(memToken)
                .memTag(memTag)
                .memImg(memImg)
                .memPass(memPass)
                .role(role.dtoToEntity())
                .ban(ban.dtoToEntity())
                .build();
    }
}
