package com.exercise.guangqi.service;

import com.exercise.guangqi.mapper.NoteMapper;
import com.exercise.guangqi.mapper.UserMapper;
import com.exercise.guangqi.model.Note;
import com.exercise.guangqi.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    final private UserMapper userMapper;
    final private HashService hashService;
    final private NoteMapper noteMapper;

    public UserService(UserMapper userMapper, HashService hashService, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.noteMapper = noteMapper;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public int uploadNote(Note note){ return noteMapper.uploadNote(note); }

    public List<Note> getAllNotesByUserId(Integer userId){
        return noteMapper.getAllNotesByUserId(userId);
    }

    public void deleteNoteById(Integer noteId){
        noteMapper.deleteNoteById(noteId);
    }

}
