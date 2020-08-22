package com.exercise.guangqi.mapper;

import com.exercise.guangqi.model.Credential;
import com.exercise.guangqi.model.Note;
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
    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username} password =#{password} userid =#{userid} WHERE credentialid =#{credentialId}")
    int updateCredential(Credential credential);

    @Delete("Delete FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredentialById(int credentialId);
}
