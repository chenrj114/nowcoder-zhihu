package com.chenrj.zhihu.service;

import com.chenrj.zhihu.dao.UserDao;
import com.chenrj.zhihu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * @ClassName UserService
 * @Description
 * @Author rjchen
 * @Date 2020-05-04 18:29
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void register(String name, String password) {
        User user = userDao.selectUserByName(name);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setSalt(UUID.randomUUID().toString().substring(0,6));
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
            userDao.insertUser(user);
        }
    }

    public String login(String name, String password) {
        User user = userDao.selectUserByName(name);
        if (user != null) {
            return UUID.randomUUID().toString().substring(0,8);
        } else {
            return null;
        }
    }

    public void deleteUser(User user) {

    }

    public void selectUser() {

    }


}
