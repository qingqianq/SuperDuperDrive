package com.exercise.guangqi.controller;

import com.exercise.guangqi.model.Credential;
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
        List<Credential> credentialList = userService.getAllCredentialsByUserId(user.getUserId());
        for(Credential c: credentialList){
            System.out.println(c.toString());
        }
        model.addAttribute("credentials", credentialList);
        return "home";
    }

    @PostMapping("/note")
    String uploadNote(Authentication authentication, Note note){
        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        if(note.getNoteId() != null){
            int insert = userService.updateNote(note);
//            if(insert < 0) model.addAttribute("addNoteErr", "addNoteError");
        }else{
            userService.uploadNote(note);
        }
        return "redirect:/home";
    }

    @GetMapping("/note/delete")
    String deleteNoteById(@RequestParam String noteid, Model model){
        userService.deleteNoteById(Integer.parseInt(noteid));
        return "redirect:/home";
    }

    @PostMapping("/credential")
    String createCredential(Authentication authentication, Credential credential){
        User user = userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());
        if(credential.getCredentialId() != null){
            userService.updateCredential(credential);
        }else{
            userService.createCredential(credential);
        }
        return "redirect:/home";
    }

    @GetMapping("/credential/delete")
    String deleteCredentialById(@RequestParam int credentialid, Model model){
        userService.deleteCredentialById(credentialid);
        return "redirect:/home";
    }
}
