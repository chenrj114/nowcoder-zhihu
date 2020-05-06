package com.chenrj.zhihu.dao;

import com.chenrj.zhihu.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName QuestionDao
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 21:01
 * @Version 1.0
 */
@Mapper
public interface QuestionDao {

    String TABLE = " question ";
    String INSERT_FIELDS = " user_id, title, content, create_date, update_date, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select({"select ", SELECT_FIELDS, " from ", TABLE, " where id = #{questionId}"})
    Question getQuestionDetail(int questionId);

    @Insert({"insert into ", TABLE, "(", INSERT_FIELDS, ") value (#{userId}, #{title}, #{content}, #{createDate}, #{updateDate}, #{commentCount})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addQuestion(Question question);


    int deleteQuestion(int questionId);

    List<Question> listLastestQuestion(int userId);

    int getCommmetCount(int questionId);

    boolean updateCommentCount(int questionId, int count);
}
