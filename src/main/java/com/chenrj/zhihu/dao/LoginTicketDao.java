package com.chenrj.zhihu.dao;

import com.chenrj.zhihu.model.LoginTicket;
import org.apache.ibatis.annotations.*;


/**
 * @ClassName LoginTicketDao
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 8:46
 * @Version 1.0
 */
@Mapper
public interface LoginTicketDao {

    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;
    String UPDATE_FIELDS = " expired, status, ticket ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") value(#{userId}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LoginTicket ticket);

    @Update({"update ", TABLE_NAME, " set expired = #{expired}, status = #{status}, ticket = #{ticket} where user_id = #{userId}"})
    void updateTicket(LoginTicket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id = #{userId}"})
    LoginTicket selectLoginTicketByUserId(@Param("userId") int userId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket selectByTicket(@Param("ticket") String ticket);

    @Update({"update ", TABLE_NAME, " set status = #{status} where user_id = #{userId}"})
    void disableTicket(@Param("userId") int userId, @Param("status") String status);
}
