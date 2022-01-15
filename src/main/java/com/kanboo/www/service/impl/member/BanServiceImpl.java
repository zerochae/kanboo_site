package com.kanboo.www.service.impl.member;

import com.kanboo.www.domain.entity.member.Ban;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.member.BanRepository;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.dto.member.BanDTO;
import com.kanboo.www.service.inter.member.BanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BanServiceImpl implements BanService {

    private final BanRepository banRepository;

    @Override
    @Transactional
    public BanDTO updateBanUser(BanDTO banDTO) {

        Member member = Member.
                builder().
                memIdx(banDTO.getMember().getMemIdx()).
                build();

        banDTO.setMember(member.entityToDto());

        Ban ban = Ban.
                builder().
                member(member).
                banIdx(banDTO.getBanIdx()).
                banEndDate(banDTO.getBanEndDate()).
                banStartDate(banDTO.getBanStartDate()).
                build();
        ban = banRepository.save(ban);

        return ban.entityToDto();

    }

    @Override
    @Transactional
    public boolean deleteBanUser(BanDTO banDTO) {

        Optional<Ban> oBan = Optional.ofNullable(banRepository.findByBanIdx(banDTO.getBanIdx()));
        if(oBan.isPresent()){
            banRepository.delete(oBan.get());
            return true;
        }
        return false;
    }
}
