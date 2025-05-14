package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardFileDTO {
    private Long id;
    private Long boardId;
    private String originalFileName;
    private String storedFileName;
}
