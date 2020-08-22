package com.exercise.guangqi.controller;

import com.exercise.guangqi.model.Credential;
import com.exercise.guangqi.model.File;
import com.exercise.guangqi.model.Note;
import com.exercise.guangqi.model.User;
import com.exercise.guangqi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        model.addAttribute("credentials", credentialList);
        List<File> fileWithoutDataList = userService.getFileListByUserid(user.getUserId());
        model.addAttribute("files",fileWithoutDataList);
        return "home";
    }

    @PostMapping("/note")
    String uploadNote(Authentication authentication, Note note, Model model){
        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        if(note.getNoteId() != null){
            int insert = userService.updateNote(note);
            if(insert < 0) model.addAttribute("error", "Update Note Error");
        }else{
            int insert = userService.uploadNote(note);
            if(insert < 0) model.addAttribute("error", "Upload Note Error");
        }
        return "redirect:/home";
    }

    @GetMapping("/note/delete")
    String deleteNoteById(@RequestParam String noteid, Model model){
        userService.deleteNoteById(Integer.parseInt(noteid));
        return "redirect:/home";
    }

    @PostMapping("/credential")
    String createCredential(Authentication authentication, Credential credential, Model model){
        User user = userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());
        if(credential.getCredentialId() != null){
            int insert = userService.updateCredential(credential);
            if(insert < 0) model.addAttribute("error", "Update Credential Error");
        }else{
            int insert = userService.createCredential(credential);
            if(insert < 0) model.addAttribute("error", "Create Credential Error");
        }
        return "redirect:/home";
    }

    @GetMapping("/credential/delete")
    String deleteCredentialById(@RequestParam int credentialid, Model model){
        userService.deleteCredentialById(credentialid);
        return "redirect:/home";
    }

    @PostMapping("/file")
    String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileupload) throws IOException {
        User user = userService.getUser(authentication.getName());
        File file = new File(null, fileupload.getOriginalFilename(),fileupload.getContentType(),
                Long.toString(fileupload.getSize()), user.getUserId(),fileupload.getBytes());
        userService.uploadFile(file);
        return "redirect:/home";
    }
}
