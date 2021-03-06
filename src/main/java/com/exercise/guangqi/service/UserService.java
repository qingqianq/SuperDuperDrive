package com.exercise.guangqi.service;

import com.exercise.guangqi.mapper.CredentialMapper;
import com.exercise.guangqi.mapper.FileMapper;
import com.exercise.guangqi.mapper.NoteMapper;
import com.exercise.guangqi.mapper.UserMapper;
import com.exercise.guangqi.model.Credential;
import com.exercise.guangqi.model.File;
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
    final private EncryptionService encryptionService;
    final private NoteMapper noteMapper;
    final private CredentialMapper credentialMapper;
    final private FileMapper fileMapper;

    public UserService(UserMapper userMapper, HashService hashService, EncryptionService encryptionService, NoteMapper noteMapper, CredentialMapper credentialMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
        this.noteMapper = noteMapper;
        this.credentialMapper = credentialMapper;
        this.fileMapper = fileMapper;
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

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public int createCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.createCredential(new Credential(null,
                credential.getUrl(),
                credential.getUsername(),
                encodedKey,
                encryptedPassword,
                credential.getUserId()));
    }

    public List<Credential> getAllCredentialsByUserId(Integer userId){
        List<Credential> credentialList = credentialMapper.getAllCredentialsByUserId(userId);
        for(Credential c: credentialList){
            String decryptedPassword = encryptionService.decryptValue(c.getPassword(),c.getKey());
            c.setPassword(decryptedPassword);
        }
        return credentialList;
    }

    public int updateCredential(Credential credential) {
        Credential c = getCredentialById(credential.getCredentialId());
        String encrptedPassword = encryptionService.encryptValue(credential.getPassword(),c.getKey());
        credential.setPassword(encrptedPassword);
        credential.setKey(c.getKey());
        return credentialMapper.updateCredential(credential);
    }

    private Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCreDentialById(credentialId);
    }

    public void deleteCredentialById(int credentialid) {
        credentialMapper.deleteCredentialById(credentialid);
    }

    public int uploadFile(File file) {
        return fileMapper.uploadFile(file);
    }

    public List<File> getFileListByUserid(Integer userId) {
        return fileMapper.getFileListByUserid(userId);
    }

    public void deleteFileById(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }

    public byte[] getFileDataById(Integer fileid) {
        return fileMapper.getFileDataById(fileid).getFiledata();
    }
}
