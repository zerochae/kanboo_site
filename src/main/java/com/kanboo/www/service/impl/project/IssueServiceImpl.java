package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.IssueRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

	private final IssueRepository issueRepository;
	private final ProjectRepository projectRepository;
	private final MemberRepository memberRepository;

	@Override
	public IssueDTO insertIssue(IssueDTO issueDTO) {
		Project project = projectRepository.findByPrjctIdx(issueDTO.getProject().getPrjctIdx());
		Member member = memberRepository.findByMemIdx(issueDTO.getMember().getMemIdx());

		issueDTO.setProject(project.entityToDto());
		issueDTO.setMember(member.entityToDto());

		Issue issue = issueDTO.dtoToEntity();
		issue.addProject(project);
		issue.addMember(member);

		IssueDTO dto = issue.entityToDto();

		Issue issue2 = issueRepository.save(dto.dtoToEntity());

		return issue2.entityToDto();
	}

	@Override
	public List<IssueDTO> IssueHandler(Long projectIdx) {
		List<Issue> issues = issueRepository.findAllByProjectIdxDesc(projectIdx);
		List<IssueDTO> returnIssues = new ArrayList<>();
		for (Issue issue : issues) {
			returnIssues.add(  issue.entityToDto() );
		}
		return returnIssues;
	}

	@Override
	@Transactional
	public IssueDTO updateIssue(IssueDTO issueDTO, String selectedIndex) {
		Issue issue = issueRepository.findByIssueIdx(issueDTO.getIssueIdx());
		issue.changeIssueState(selectedIndex);
		return issue.entityToDto();
	}

	@Override
	public List<IssueDTO> getLastestIssue(Long projectIdx) {
		List<Issue> issueList = issueRepository.findAllByProjectIdxDesc(projectIdx);
		List<IssueDTO> issueDTOS = new ArrayList<>();
		issueList.forEach(item -> {
			issueDTOS.add(item.entityToDto());
		});
		return issueDTOS;
	}

}
