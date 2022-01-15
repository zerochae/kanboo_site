package com.kanboo.www.controller.global;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.board.boardQueryDSL.BoardDSLRepositoryImpl;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.board.BoardFileDTO;
import com.kanboo.www.dto.board.CommentDTO;
import com.kanboo.www.dto.board.LikesDTO;
import com.kanboo.www.dto.global.CodeDetailDto;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.board.BoardFileService;
import com.kanboo.www.service.inter.board.BoardService;
import com.kanboo.www.service.inter.board.CommentService;
import com.kanboo.www.service.inter.board.LikeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private static final Logger logger
            = LoggerFactory.getLogger(BoardDSLRepositoryImpl.class);

    private final BoardService boardService;
    private final BoardFileService boardFileService;
    private final LikeService likeService;
    private final JwtSecurityService jwtSecurityService;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @GetMapping("/boardTest")
    public List<BoardDTO> testBoard(String selected, String key, int articleOnvView, String codeDetail, String token) {
//        String memTag = jwtSecurityService.getToken(token);
//        System.out.println(memTag);
//
        List<BoardDTO> allList = boardService.getAllList(selected, key, articleOnvView, codeDetail, token);
        return allList;
    }

    @GetMapping("/getArticleNum")
    public long getArticleNum(String key, String selected, String codeDetails) {
        return boardService.getArticleNum(key, selected, codeDetails);
    }

    @GetMapping("/BoardComment")
    public List<CommentDTO> getComments(long boardIdx, int commentsOnView){
        return boardService.getComments(boardIdx, commentsOnView);
    }

    @GetMapping("/DeleteBoard")
    public boolean deleteBoard(long boardIdx) {
        return boardService.deleteBoard(boardIdx);

    }

    @PostMapping("/insertBoard")
    public BoardDTO insertBoard(@RequestBody Map<String, Object> map){

        Member member = memberRepository.findByMemTag(map.get("token") + "");
        CodeDetailDto codeDetail = CodeDetailDto.builder()
                .codeDetailIdx(map.get("codeDetailIdx") + "")
                .build();

        BoardDTO dto = BoardDTO.builder()
                .boardCn(map.get("boardCn") + "")
                .delAt("N")
                .fileAt(map.get("fileAt") + "")
                .totalComments(0)
                .totalLikes(0)
                .codeDetail(codeDetail)
                .build();
        BoardDTO boardDTO = boardService.insertBoard(dto, member.getMemIdx());
        return boardDTO;
    }

    @PostMapping("/updateBoard")
    public BoardDTO updateBoard(@RequestBody Map<String, Object> map){
        //원래는 이 부분에서 session에 담긴 MemberDTO를 꺼내와서 decoding 하고 멤버 유효성을 검사하고 메소드 실행
        return boardService.updateBoard(map);
    }

    @PostMapping("/insertFile")
    public BoardFileDTO insertFile(String fileSize, String fileName,
                                   String extensionName, long boardIdxToInsertFile,
                                   int category, @ModelAttribute MultipartFile file,
                                   String checkInsertOrUpdate){
        long memIdx = 138L;
        BoardDTO boardDTO = BoardDTO.builder()
                .boardIdx(boardIdxToInsertFile)
                .build();
        BoardFileDTO fileDTO = BoardFileDTO.builder()
                .fileSize(fileSize)
                .fileName(fileName)
                .extensionName(extensionName)
                .board(boardDTO)
                .build();

        return boardFileService.insertFiles(fileDTO, file, category, memIdx, checkInsertOrUpdate);
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent,
                                                 @RequestBody Map<String, String> map){

        return boardFileService.downloadFile(userAgent, map);
    }

    @GetMapping("/updateLike")
    public BoardDTO updateLike(long boardIdx, String token){
        Member byMemToken = memberRepository.findByMemTag(token);
        Long memIdx = byMemToken.getMemIdx();
        LikesDTO likesDTO = likeService.insertLike(boardIdx, memIdx);

        if(likesDTO != null){
            return boardService.updateLikes(boardIdx);
        }

        return null;
    }

    @GetMapping("/deleteLike")
    public BoardDTO deleteLike(long boardIdx, String token){

        Member byMemTag = memberRepository.findByMemTag(token);
        Long memIdx = byMemTag.getMemIdx();
        int deleteResult = likeService.deleteLike(boardIdx, memIdx);

        if(deleteResult != 0){
            return boardService.decreaseLikesNum(boardIdx);
        }
        return null;
    }

    @PostMapping("/insertComment")
    public CommentDTO insertComment(@RequestBody Map<String, Object> map){
        Member member = memberRepository.findByMemTag(map.get("token") + "");
        //answer테이블에 insert  하고 board 테이블에서 total_comments += 1 update
        BoardDTO boardDTO = BoardDTO.builder()
                .boardIdx(Long.parseLong(String.valueOf(map.get("boardIdx"))))
                .build();
        MemberDTO memberDTO = MemberDTO.builder()
                .memNick(member.getMemNick())
                .memIdx(member.getMemIdx())
                .build();

        CommentDTO commentDTO = CommentDTO.builder()
                .member(memberDTO)
                .board(boardDTO)
                .answerDelAt(map.get("answerDelAt") + "")
                .answerDate(LocalDateTime.now())
                .answerCn(map.get("answerCn") + "")
                .build();
        BoardDTO returnedBoard = boardService.increaseTotalComments(Long.parseLong(map.get("boardIdx") + ""));
        if(returnedBoard != null){
            return commentService.insertComment(commentDTO);
        }
        return null;
    }
}
