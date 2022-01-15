package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRepository extends JpaRepository<Chat, Long> {
    Chat findByProject_PrjctIdx(Long prjctIdx);
    Chat findByMember_MemIdx(Long memIdx);
}
