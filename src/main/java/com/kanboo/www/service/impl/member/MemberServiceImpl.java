package com.kanboo.www.service.impl.member;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.dto.global.RoleDto;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.service.inter.member.MemberService;
import com.kanboo.www.util.CreateKTag;
import com.kanboo.www.util.CreateTempPw;
import com.kanboo.www.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDTO loginHandler(MemberDTO memberDTO) {

        Member member = null;

        try {
            member = memberRepository.findByMemIdAndMemPass(
                    memberDTO.getMemId(),
                    CryptoUtil.encryptSha512(memberDTO.getMemPass())
            );
            return member.entityToDto();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public MemberDTO signHandler(MemberDTO memberDTO) {

        String kTag, toKen = "";

        do {
            kTag = CreateKTag.create();
        } while (isExistKTag(kTag) > 0);

        do {
            toKen = UUID.randomUUID().toString();
        } while (isExistToken(toKen) > 0);

        String password = memberDTO.getMemPass();

        try {
            memberDTO.setMemPass(CryptoUtil.encryptSha512(password));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        memberDTO.setRole(new RoleDto(1L, "ROLE_MEMBER"));

        Member beforeMember = Member.builder()
                .memIdx(memberDTO.getMemIdx())
                .memId(memberDTO.getMemId())
                .memNick(memberDTO.getMemNick())
                .memCelNum(memberDTO.getMemCelNum())
                .memToken(toKen)
                .memTag(kTag)
                .memImg(memberDTO.getMemImg())
                .memPass(memberDTO.getMemPass())
                .role(new RoleDto(2L, "ROLE_MEMBER").dtoToEntity())
                .build();

        Member member = memberRepository.save(beforeMember);

        return member.entityToDto();
    }

    @Override
    public int isExistKTag(String kTag) {
        return memberRepository.countByMemTag(kTag);
    }

    @Override
    public int isExistToken(String token) {
        return memberRepository.countByMemToken(token);
    }

    @Override
    public int isExistId(String memId) {
        return memberRepository.countByMemId(memId);
    }

    @Override
    public MemberDTO findIdHandler(MemberDTO memberDTO) {
        return memberRepository.findByMemToken(memberDTO.getMemToken()).entityToDto();
    }

    @Override
    @Transactional
    public String resetPwHandler(MemberDTO memberDTO) {
        Member member = memberRepository.findByMemToken(memberDTO.getMemToken());
        String newPw = null;

        if (member != null) {
            newPw = CreateTempPw.create();
            member.changeMemPass(newPw);
        }

        return newPw;
    }

    @Override
    public MemberDTO getUserInfo(String memTag) {
        Member member = memberRepository.findByMemTag(memTag);
        return MemberDTO.builder()
                .memIdx(member.getMemIdx())
                .memId(member.getMemId())
                .memNick(member.getMemNick())
                .memTag(member.getMemTag())
                .memCelNum(member.getMemCelNum())
                .memImg(member.getMemImg())
                .build();
    }

    @Override
    @Transactional
    public Boolean userModify(MemberDTO memberDTO) {
        try{
            memberDTO.setMemPass(CryptoUtil.encryptSha512(memberDTO.getMemPass()));
        } catch (Exception e) {
            return false;
        }
        Member member = memberRepository.findByMemIdAndMemPass(memberDTO.getMemId(), memberDTO.getMemPass());
        if(member != null) {
            member.changeMemPass(memberDTO.getMemPass());
            member.changeMemCelNum(memberDTO.getMemCelNum());
            member.changeMemNick(memberDTO.getMemNick());
            member.changeMemImg(memberDTO.getMemImg());
            return true;
        }
        return false;
    }

    @Override
    public List<MemberDTO> getAllMember(String selected, String keyword, int articleOnView) {

        List<Member> all = memberRepository.findAllMemberBanInfo(selected,keyword,articleOnView);
        List<MemberDTO> result = new ArrayList<>();
        for (Member m : all) {
            MemberDTO memberDTO = m.entityToDto();
            if(m.getBan() != null) {
                memberDTO.setBan(m.getBan().entityToDto());
            }
            result.add(memberDTO);
        }
        return result;
    }

    @Override
    public Long getMaxIndexOfMember(String selected, String key) {
        return memberRepository.getMaxIndexOfMember(selected,key);
    }
}
