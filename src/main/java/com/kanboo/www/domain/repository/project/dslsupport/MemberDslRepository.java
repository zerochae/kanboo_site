package com.kanboo.www.domain.repository.project.dslsupport;


import com.kanboo.www.domain.entity.member.Ban;
import com.kanboo.www.domain.entity.member.Member;

import java.util.List;

public interface MemberDslRepository {
	Member findByMemberIdx(Long memberIdx);

	List<Member> findAllMemberBanInfo(String selected, String keyword, int articleOnView);

	long getMaxIndexOfMember(String keyword, String selected);
}
