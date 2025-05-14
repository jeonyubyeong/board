package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.BoardFileDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String save(HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        return "save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO, HttpSession session) throws IOException {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String findAll(Model model, HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileDTOList", boardFileDTOList);
        }

        return "detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        boardService.update(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/?required=true";
        }
        boardService.delete(id);
        return "redirect:/list";
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) throws IOException {
        BoardFileDTO fileDTO = boardService.findFileById(id);
        if (fileDTO == null) {
            return ResponseEntity.notFound().build();
        }

        String storedFileName = fileDTO.getStoredFileName();
        String originalFileName = fileDTO.getOriginalFileName();
        String filePath = "src/main/resources/upload_files/" + storedFileName;

        Resource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
