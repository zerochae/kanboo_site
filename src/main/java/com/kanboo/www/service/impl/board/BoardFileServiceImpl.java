package com.kanboo.www.service.impl.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.BoardFile;
import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.board.BoardFileRepository;
import com.kanboo.www.dto.board.BoardFileDTO;
import com.kanboo.www.service.inter.board.BoardFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {

    private final BoardFileRepository boardFileRepository;

    @Transactional
    @Override
    public BoardFileDTO insertFiles(BoardFileDTO dto, MultipartFile file, int category, long memIdx, String checkInsertOrUpdate){


        String uploadPath = "";
        File f = new File("");
        String absolutePath = f.getAbsolutePath();
        String rootPath = "";
        File uploadFile = new File("","");
        String originalFileName = file.getOriginalFilename();
        File saveFile = new File("","");

        if(category == 7){
            uploadPath = "/src/main/resources/storage/board/free";
        } else if(category == 8){
            uploadPath = "/src/main/resources/storage/board/qna";
        }

        rootPath = absolutePath + uploadPath;
        uploadFile = new File(rootPath, getFolder(dto.getBoard().getBoardIdx(), memIdx));
        if(!uploadFile.exists()){
            uploadFile.mkdirs();
        }
        originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
        saveFile = new File(uploadFile, originalFileName);

        if(checkInsertOrUpdate.equals("update")){
            BoardFile presentFile = boardFileRepository.getPresentFile(dto.getBoard().getBoardIdx());
            if(presentFile != null){
                File existingFile = new File(uploadFile, presentFile.getFileName());
                if(existingFile.delete()){
                    try {
                        boardFileRepository.deleteFile(dto.getBoard().getBoardIdx());
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }


        try {
            if(!file.isEmpty()){
                file.transferTo(saveFile);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        Board board = Board.builder()
                .boardIdx(dto.getBoard().getBoardIdx())
                .member(new Member())
                .codeDetail(new CodeDetail())
                .build();
        BoardFile boardFile = BoardFile.builder()
                .extensionName(dto.getExtensionName())
                .fileName(dto.getFileName())
                .fileSize(dto.getFileSize())
                .filePath(uploadFile.toString())
                .board(board)
                .build();

        BoardFile savedFile = boardFileRepository.save(boardFile);
        return savedFile.entityToDto();
    }

    //계층형 폴더
    //boardIdx, memIdx 필요
    @Override
    public String getFolder(long boardIdx, long memIdx){
        String dynamicPath = memIdx + "/" + boardIdx;
        return dynamicPath;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(@RequestHeader String userAgent,
                                                 @RequestBody Map<String, String> map) {
        File file = new File("");
        String downloadPath = "";
        String absolutePath = file.getAbsolutePath();
        String codeDetail = (String) map.get("codeDetail");
        if(codeDetail.equals("7")){
            downloadPath ="/src/main/resources/storage/board/free";
        }else if(codeDetail.equals("8")){
            downloadPath ="/src/main/resources/storage/board/qna";
        }
        String finalPath
                = absolutePath + downloadPath + "/" + (String)map.get("memIdx") + "/" + (String)map.get("boardIdx");
        Resource resource = new FileSystemResource(finalPath + "/" + (String)map.get("fileName"));

        if(!resource.exists()){
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();
        try {
            String downloadName = null;
            if(userAgent.contains("Trident")){
                downloadName = URLEncoder.encode(resourceName, "UTF-8").replaceAll("\\+", " ");
            } else if(userAgent.contains("Edg")){
                downloadName = URLEncoder.encode(resourceName, "UTF-8");
            } else {
                downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
            }

            headers.add("Content-disposition", "attachment;filename=" + downloadName);
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
}
