package com.chenrj.zhihu.service;

import com.chenrj.zhihu.dao.LoginTicketDao;
import com.chenrj.zhihu.dao.UserDao;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.model.LoginTicket;
import com.chenrj.zhihu.model.User;
import com.chenrj.zhihu.result.ResultStatus;
import com.chenrj.zhihu.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName UserService
 * @Description
 * @Author rjchen
 * @Date 2020-05-04 18:29
 * @Version 1.0
 */
@Slf4j
@Service
public class UserService {

    private static final int DURATION_DAY = 3;
    private static final long DURATION_TIME = DURATION_DAY * 24 * 3600 * 1000;

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao ticketDao;

    @Resource
    HostHolder currentUser;

    public ResultStatus register(String name, String password) {
        User user = getUser(name);
        if (user == null) {
            String salt = UUID.randomUUID().toString().substring(0,6);
            user = new User();
            user.setName(name);
            user.setPassword(SecurityUtil.MD5(password + salt));
            user.setSalt(salt);
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
            userDao.addUser(user);
            ticketDao.addTicket(generateTicket(getUser(name).getId()));
            currentUser.setUsers(user);
            return ResultStatus.REGISTER_SUCCESS;
        } else {
            return ResultStatus.REGISTER_FAIL;
        }
    }

    public ResultStatus login(String name, String password) {
        User user = userDao.selectUserByName(name);
        if (user != null) {
            String encryptedPassword = SecurityUtil.MD5(password + user.getSalt());
            if (encryptedPassword.equals(user.getPassword())) {
                log.info("登录成功");
                currentUser.setUsers(user);
                ticketDao.updateTicket(generateTicket(user.getId()));
                return ResultStatus.LOGIN_SUCCESS;
            }
        }
        return ResultStatus.LOGIN_FAIL;
    }

    /**
     * 登出的操作: 1. 清除用户的Ticket, 2. 当前线程内清除用户对象
     */
    public void logout() {
        log.info("进入登出命令");
        if (currentUser.getUser() == null) {
            log.info(" 当前currentUser.getUser() 为空 用户已经退出, 无法清楚ticket");
        }
        log.info(currentUser.getUser().getId().toString() );
        ticketDao.disableTicket(currentUser.getUser().getId(), "1");
        currentUser.clear();
    }

    /**
     * 功能说明: 随机生成一个用户的ticket
     * @param userId
     * @return ticket
     */
    public LoginTicket generateTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + DURATION_TIME);
        ticket.setExpired(date);
        ticket.setStatus("0");
        ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        return ticket;
    }

    public User getUser(String name) {
        return userDao.selectUserByName(name);
    }

    public User getUser(int userId) {
        return userDao.selectUserById(userId);
    }

    public String getCookie(String name) {
        User user = userDao.selectUserByName(name);
        return ticketDao.selectLoginTicketByUserId(user.getId()).getTicket();
    }

    public User getUserByTicket(String ticket) {

        LoginTicket loginTicket = ticketDao.selectByTicket(ticket);
        if (loginTicket == null || loginTicket.getStatus().equals("1") || loginTicket.getExpired().before(new Date())) {
            return null;
        }
        return userDao.selectUserById(loginTicket.getUserId());
    }

    public void deleteUser(User user) {

    }
}
