package com.exercise.guangqi.controller;

import com.exercise.guangqi.model.Credential;
import com.exercise.guangqi.model.File;
import com.exercise.guangqi.model.Note;
import com.exercise.guangqi.model.User;
import com.exercise.guangqi.service.UserService;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


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
    String deleteNoteById(@RequestParam Integer noteid, Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        List<Note> noteList = userService.getAllNotesByUserId(user.getUserId());
        Optional<Note> note = noteList.stream().filter(n -> n.getNoteId().equals(noteid)).findFirst();
        if(note.isPresent())
            userService.deleteNoteById(noteid);
        else
            model.addAttribute("error", "Do not have auth");
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
    String deleteCredentialById(@RequestParam Integer credentialid, Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        List<Credential> credentialList = userService.getAllCredentialsByUserId(user.getUserId());
        Optional<Credential> credential =
                credentialList.stream().filter(c -> c.getCredentialId().equals(credentialid)).findFirst();
        if(credential.isPresent())
            userService.deleteCredentialById(credentialid);
        else
            model.addAttribute("error", "Do not have auth");
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

    //Make sure the user can not delete the file he does not owns.

    @GetMapping("/file/delete")
    String deleteFileById(Authentication authentication, @RequestParam("fileid") Integer fileId, Model model){
        User user = userService.getUser(authentication.getName());
        List<File> fileList = userService.getFileListByUserid(user.getUserId());
        Optional<File> file = fileList.stream().filter(f -> f.getFileId().equals(fileId)).findFirst();
        if(file.isPresent())
            userService.deleteFileById(fileId);
        else
            model.addAttribute("error", "Do not have authentication");
        return "redirect:/home";
    }

    @GetMapping(value = "/file/{fileid}")
    @ResponseBody
    ResponseEntity<?> fileView(Authentication authentication, @PathVariable Integer fileid){
        User user = userService.getUser(authentication.getName());
        List<File> fileList = userService.getFileListByUserid(user.getUserId());
        Optional<File> file = fileList.stream().filter(f -> f.getFileId().equals(fileid)).findFirst();
        if(file.isPresent()){
            String contentType = file.get().getContentType();
            byte[] data = userService.getFileDataById(fileid);
            if(data != null){
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(contentType));
                return new ResponseEntity<>(data,headers, HttpStatus.OK);
            }
        }
        String errorMessage = fileid.toString() + " not found.";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
