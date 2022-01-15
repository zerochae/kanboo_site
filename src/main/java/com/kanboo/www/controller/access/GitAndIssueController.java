package com.kanboo.www.controller.access;

import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.IssueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gitAndIssue")
public class GitAndIssueController {

	private final IssueService issueService;
	private static final Logger logger =
			LoggerFactory.getLogger(GitAndIssueController.class);

	@PostMapping(value="/insert")
	public IssueDTO insertIssue(IssueDTO issueDTO){
		return issueService.insertIssue(issueDTO);
	}

	@GetMapping(value = "/getAllList")
	public List<IssueDTO> IssueHandler(ProjectDTO project){
		return issueService.IssueHandler(project.getPrjctIdx());
	}

	@PostMapping(value = "/updateIssue")
	public IssueDTO updateIssue(IssueDTO issueDTO, String selectedIndex){
		return	issueService.updateIssue(issueDTO, selectedIndex);
	}
}
