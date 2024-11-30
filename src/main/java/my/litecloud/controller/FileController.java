package my.litecloud.controller;

import my.litecloud.dto.FileReadDTO;
import my.litecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                .toList();
        model.addAttribute("files", files);
        return "files.html";
    }

    @GetMapping(path = "/update")
    public String update(@RequestParam("id") Long id, @RequestParam("shared") Boolean shared) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.shareFile(username, id, shared);
        System.out.println("set share " + shared);
        return "redirect:/files/list";
    }

}
