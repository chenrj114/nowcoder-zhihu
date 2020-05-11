package com.chenrj.zhihu.service;

import com.chenrj.zhihu.dao.CommentDao;
import com.chenrj.zhihu.model.Comment;
import com.chenrj.zhihu.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CommentService
 * @Description
 * @Author rjchen
 * @Date 2020-05-06 16:49
 * @Version 1.0
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Resource
    HostHolder currentUser;

    /**
     * 功能: 添加评论
     * @return 返回评论的id
     */
    public int addComment(int questionId, String content) {
        Comment comment = new Comment();
        comment.setEntityId(questionId);
        comment.setEntityType(Comment.CommentedType.QUESTION);
        comment.setContent(content);
        comment.setUserId(currentUser.getUser().getId());
        comment.setCreateDate(new Date());
        commentDao.addComment(comment);
        return comment.getId();
    }

    /**
     * 功能: 获取某个问题的所有的评论
     * @return
     */
    public List<Comment> getQuestionComment(int questionId) {
        return commentDao.getQuestionComment(questionId);
    }

    /**
     * 功能: 获取某个用户的所有的评论
     * @param userid
     * @return
     */
    public List<Comment> getUserComent(int userid) {
        return null;
    }
}
