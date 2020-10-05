package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.model.Message;
import com.chenrj.zhihu.model.User;
import com.chenrj.zhihu.service.MessageService;
import com.chenrj.zhihu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author rjchen
 * @date 2020/9/21
 */
@Controller
@Slf4j
public class MessageController {

    @Autowired
    HostHolder currentUser;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/message/list/", method = RequestMethod.GET)
    public ModelAndView getConversationList() {
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser.getUser() == null) {
            modelAndView.setViewName("redirect:/signin");
            return modelAndView;
        }
        int userId = currentUser.getUser().getId();
        List<Message> conversations = messageService.getMessageByUserId(userId);
        log.info("共有 {} 封站内信", conversations.size());
        modelAndView.setViewName("letter");
        modelAndView.addObject("user", currentUser.getUser());
        modelAndView.addObject("conversations", conversations);
        return modelAndView;
    }

    @RequestMapping(path = "/message/send/", method = RequestMethod.POST)
    public String sendMessage(@Param("recipientName") String recipientName,
                            @Param("content") String content) {
        User recipient = userService.getUser(recipientName);
        //TODO 发送站内信 收信人为空 未处理
        if (recipient == null) {
            return "没有此用户";
        }
        messageService.sendMessage(currentUser.getUser().getId(), recipient.getId(), content);
        return "发送成功";
    }

}
