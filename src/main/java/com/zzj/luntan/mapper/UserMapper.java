package com.zzj.luntan.mapper;/*
 *created by xiaozhang
 *@Date: 2022/06/01/20:18
 *@Description:
 */

import com.zzj.luntan.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);


    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token")String token);
}
