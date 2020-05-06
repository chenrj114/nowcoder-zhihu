package com.chenrj.zhihu.service;

import com.chenrj.zhihu.dao.QuestionDao;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName QuestionService
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 21:11
 * @Version 1.0
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Resource
    HostHolder currentUser;

    public Question getQuestionDatil(int questionId) {
        return questionDao.getQuestionDetail(questionId);
    }

    /**
     * 用户提出一个问题
     * @param title
     * @param content
     * @return 用户提出的问题的id
     */
    public int addQuestion(String title,  String content) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        Date date = new Date();
        question.setCreateDate(date);
        question.setUpdateDate(date);
        question.setUserId(currentUser.getUser().getId());
        questionDao.addQuestion(question);
        return question.getId();
    }

    public boolean deleteQuestion(int questionId) {
        questionDao.deleteQuestion(questionId);
        return false;
    }

    public List<Question> getLatestQuestion(int userId) {
        return questionDao.listLastestQuestion(userId);
    }

    public int getCommentCount(int questionId) {
        return questionDao.getCommmetCount(questionId);
    }

    public boolean updateCommentCount(int questionId, int count) {
        return questionDao.updateCommentCount(questionId, count);
    }
}
