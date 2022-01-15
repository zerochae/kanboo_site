package com.kanboo.www.domain.repository.member;

import com.kanboo.www.domain.entity.member.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
    Ban findByBanIdx(Long banIdx);
}
