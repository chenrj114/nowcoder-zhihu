package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @ClassName CommentController
 * @Description
 * @Author rjchen
 * @Date 2020-05-11 13:52
 * @Version 1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {

        commentService.addComment(questionId, content);
        return "redirect:/question/" + questionId;
    }
}
