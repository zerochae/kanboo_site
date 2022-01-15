package com.kanboo.www.service.inter.member;

import com.kanboo.www.dto.member.BanDTO;

public interface BanService {
    BanDTO updateBanUser(BanDTO banDTO);

    boolean deleteBanUser(BanDTO banDTO);
}
