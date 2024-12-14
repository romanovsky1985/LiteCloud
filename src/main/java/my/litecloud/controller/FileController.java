package my.litecloud.controller;

import my.litecloud.dto.FileReadDTO;
import my.litecloud.dto.FileCreateDTO;
import my.litecloud.page.files.EditPage;
import my.litecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(path = "/files")
public class FileController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/list")
    public String list(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<FileReadDTO> files = userService.getFiles(username).stream()
                .sorted(Comparator.comparing(FileReadDTO::getUpDateTime, LocalDateTime::compareTo))
                .toList();
        model.addAttribute("files", files);
        return "files.html";
    }

    @GetMapping(path = "/update")
    public String update(@RequestParam("id") Long id, @RequestParam("shared") Boolean shared) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.shareFile(username, id, shared);
        return "redirect:/files/list";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteFile(username, id);
        return "redirect:/files/list";
    }

    @GetMapping(path = "/download")
    public ResponseEntity<byte[]> download(@RequestParam("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String filename = userService.getFileName(username, id);
        byte[] data = userService.getFileData(username, id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(data);
    }

    @GetMapping(path = "/shared")
    public ResponseEntity<byte[]> sharedDownload(@RequestParam("id") Long id) {
        String filename = userService.getFileName(id);
        byte[] data = userService.getFileData(id);
        return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                 .body(data);
    }

    @PostMapping(path = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, FileCreateDTO createDTO) 
            throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        createDTO.setName(file.getOriginalFilename());
        createDTO.setData(file.getBytes());
        userService.appendFile(username, createDTO);
        return "redirect:/files/list";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model, @RequestParam("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        EditPage page = new EditPage();
        page.setContent(new String(userService.getFileData(username, id)));
        model.addAttribute("page", page);
        return "/files/edit.html";
    }

}
