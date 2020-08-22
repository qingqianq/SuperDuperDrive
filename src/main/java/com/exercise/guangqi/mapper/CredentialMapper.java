package com.exercise.guangqi.mapper;

import com.exercise.guangqi.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS where userid = #{userId}")
    List<Credential> getAllCredentialsByUserId(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
            " VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);


    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialId}")

    int updateCredential(Credential credential);

    @Delete("Delete FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredentialById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCreDentialById(Integer credentialId);
}
