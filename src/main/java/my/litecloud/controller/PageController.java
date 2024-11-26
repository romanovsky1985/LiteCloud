package my.litecloud.controller;

import my.litecloud.dto.PageDTO;
import my.litecloud.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping(path = "/pages")
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/list")
    public String list(Model model, @RequestParam(value = "shows", defaultValue = "no") String shows) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PageDTO> pages = userService.getPages(username).stream()
                .sorted(Comparator.comparing(PageDTO::getText, String::compareTo))
                .toList();
        List<String> sections = pages.stream()
                .map(PageDTO::getSection)
                .distinct()
                .sorted()
                .toList();
        model.addAttribute("pages", pages);
        model.addAttribute("sections", sections);
        model.addAttribute("shows", shows);
        return "pages.html";
    }

    @PostMapping(path = "/create")
    public String create(PageDTO pageDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.appendPage(username, pageDTO);
        return "redirect:/pages/list";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deletePage(username, id);
        return "redirect:/pages/list";
    }
}