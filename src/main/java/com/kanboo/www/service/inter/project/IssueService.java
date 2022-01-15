package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.IssueDTO;

import java.util.List;

public interface IssueService {

	IssueDTO insertIssue(IssueDTO issueDTO);

	List<IssueDTO> IssueHandler(Long projectIdx);

	IssueDTO updateIssue(IssueDTO issueDTO, String selectedIndex);

	List<IssueDTO> getLastestIssue(Long projectIdx);
}
