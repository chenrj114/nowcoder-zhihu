package com.chenrj.zhihu.dao;

import com.chenrj.zhihu.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName UserDao
 * @Description
 * @Author rjchen
 * @Date 2020-05-04 20:58
 * @Version 1.0
 */
@Mapper
public interface UserDao {

    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_fIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME , " (" + INSERT_FIELDS, ") values ",
                     "( #{name}, #{password}, #{salt}, #{headUrl} )"})
    int addUser(User user);

    @Select({"select ", SELECT_fIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectUserByName(@Param("name") String name);

    @Select({"select ", SELECT_fIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectUserById(@Param("id") int id);
}
