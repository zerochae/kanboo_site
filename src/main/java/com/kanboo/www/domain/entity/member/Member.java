package com.kanboo.www.domain.entity.member;

import com.kanboo.www.domain.entity.global.Role;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memIdx;

    private String memId;
    private String memNick;
    private String memCelNum;
    private String memToken;
    private String memTag;
    private String memImg;
    private String memPass;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleIdx")
    private Role role;

    @OneToOne(mappedBy = "member")
    private Ban ban;

    public MemberDTO entityToDto() {
        return MemberDTO.builder()
                .memIdx(memIdx)
                .memId(memId)
                .memNick(memNick)
                .memCelNum(memCelNum)
                .memToken(memToken)
                .memTag(memTag)
                .memImg(memImg)
                .memPass(memPass)
                .build();
    }


    public void changeMemPass(String memPass) {
        this.memPass = memPass;
    }
    public void changeMemNick(String memNick) { this.memNick = memNick; }
    public void changeMemCelNum(String memCelNum) { this.memCelNum = memCelNum; }
    public void changeMemImg(String memImg) { this.memImg = memImg; }
}
