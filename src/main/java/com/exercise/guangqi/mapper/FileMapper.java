package com.exercise.guangqi.mapper;

import com.exercise.guangqi.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userId, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Select("SELECT fileId, filename, contenttype, userid FROM FILES where userid = #{userId}")
    List<File> getFileListByUserid(Integer userId);

    @Delete("Delete FROM FILES WHERE fileid = #{fileId}")
    void deleteFileById(Integer fileId);


    @Select("SELECT filedata FROM FILES where fileid = #{fileId}")
    File getFileDataById(Integer fileid);
}
