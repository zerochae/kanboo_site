package com.kanboo.www.domain.entity.board;

import com.kanboo.www.dto.board.BoardFileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
@Builder
public class BoardFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    private String fileName;
    private String filePath;
    private String fileSize;
    private String extensionName;

    public BoardFileDTO entityToDto() {
        return BoardFileDTO.builder()
                .fileIdx(fileIdx)
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .extensionName(extensionName)
                .build();
    }
}
