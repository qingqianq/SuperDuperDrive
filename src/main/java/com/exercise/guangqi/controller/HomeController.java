package com.exercise.guangqi.controller;

import com.exercise.guangqi.model.Note;
import com.exercise.guangqi.model.User;
import com.exercise.guangqi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String getHomeView(Authentication authentication, Model model){
        User user = userService.getUser(authentication.getName());
        List<Note> noteList = userService.getAllNotesByUserId(user.getUserId());
            model.addAttribute("notes",noteList);
        return "home";
    }

    @PostMapping("/note")
    String uploadNote(Authentication authentication, Note note, Model model){
        User user = userService.getUser(authentication.getName());
        userService.uploadNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), user.getUserId()));
        return "redirect:/home";
    }

    @GetMapping("/note/delete")
    String deleteNoteById(Authentication authentication,
                          @RequestParam String noteid, Model model){
        userService.deleteNoteById(Integer.parseInt(noteid));
        return "redirect:/home";
    }

}
