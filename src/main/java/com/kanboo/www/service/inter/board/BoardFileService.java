package com.kanboo.www.service.inter.board;

import com.kanboo.www.dto.board.BoardFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BoardFileService {

    BoardFileDTO insertFiles(BoardFileDTO dto, MultipartFile file, int category, long memIdx, String checkInsertOrUpdate);

    String getFolder(long boardIdx, long memIdx);

    ResponseEntity<Resource> downloadFile(String userAgent, Map<String, String> map);
}
