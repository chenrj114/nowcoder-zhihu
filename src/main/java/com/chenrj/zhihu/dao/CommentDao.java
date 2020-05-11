package com.chenrj.zhihu.dao;

import com.chenrj.zhihu.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName CommentDao
 * @Description
 * @Author rjchen
 * @Date 2020-05-06 16:57
 * @Version 1.0
 */
@Mapper
public interface CommentDao {

    String TABLE = " comment ";
    String INSERT_FIELDS = " user_id, entity_id, entity_type, content, create_date ";
    String SELETE_FIELDS = " id, " + INSERT_FIELDS + " , like_count, dislike_count ";

    @Insert({"insert into ", TABLE, " (", INSERT_FIELDS, " ) value ( #{userId}, #{entityId}, #{entityType}, #{content}, #{createDate} ) "})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addComment(Comment comment);

    @Select({"select ", SELETE_FIELDS, " from ", TABLE, " where entity_id = #{questionId} and entity_type = 'QUESTION' limit 20 "})
    List<Comment> getQuestionComment(int questionId);

    @Select({"select ", SELETE_FIELDS, " from ", TABLE, " where user_id = #{userId} limit 20"})
    List<Comment> getUserComment(int usrId);
}
