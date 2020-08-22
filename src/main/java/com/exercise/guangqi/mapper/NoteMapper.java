package com.exercise.guangqi.mapper;

import com.exercise.guangqi.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES where userid = #{userId}")
    List<Note> getAllNotesByUserId(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int uploadNote(Note note);

    @Delete("Delete FROM NOTES WHERE noteid = #{noteId}")
    void deleteNoteById(Integer noteId);
}
