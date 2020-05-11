package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.model.Comment;
import com.chenrj.zhihu.model.Question;
import com.chenrj.zhihu.model.User;
import com.chenrj.zhihu.service.CommentService;
import com.chenrj.zhihu.service.QuestionService;
import com.chenrj.zhihu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName QuestionController
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 21:43
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    /**
     * 功能: 某个问题的问题详情
     * @param modelAndView
     * @param questionId
     * @return
     */
    @RequestMapping("/{questionId}")
    public ModelAndView questionDetail(ModelAndView modelAndView, @PathVariable("questionId") int questionId) {
        Question question = questionService.getQuestionDatil(questionId);
        modelAndView.addObject("question", question);
        modelAndView.setViewName("questionDetail");
        List<Comment> comments = commentService.getQuestionComment(questionId);
        List<User> userList = new ArrayList<>();
        Map<Comment, User> commentsMap = new HashMap<>();
        if (comments != null) {
            for (Comment comment : comments) {
                log.info(comment.toString());
                User commentor = userService.getUser(comment.getUserId());
                commentsMap.put(comment, commentor);
            }
        }
        modelAndView.addObject("comments", commentsMap);
        return modelAndView;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ModelAndView addQUestion(ModelAndView modelAndView, @RequestParam("title") String title, @RequestParam("content") String content) {
        log.info("add question ...");
        int questionId = questionService.addQuestion(title, content);
        modelAndView.setViewName("redirect:/question/"+questionId);
        return modelAndView;
    }

    /**
     * 功能: 获取某个用户的提出的所有的问题的列表
     * @param modelAndView
     * @param userId
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView getUserQuestion(ModelAndView modelAndView, @RequestParam("user") int userId) {
        List<Question> questions = questionService.getLatestQuestion(userId);
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }
}
