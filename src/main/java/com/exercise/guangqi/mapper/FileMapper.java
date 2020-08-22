package com.exercise.guangqi.mapper;

import com.exercise.guangqi.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, fileid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileId}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Select("SELECT fileId, filename, contenttype, userid FROM FILES where userid = #{userId}")
    List<File> getFileListByUserid(Integer userId);
}
