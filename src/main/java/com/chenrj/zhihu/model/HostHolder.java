package com.chenrj.zhihu.model;

import org.springframework.stereotype.Component;

/**
 * @ClassName HostHolder
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 12:59
 * @Version 1.0
 */
@Component("CurrentUser")
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUsers(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
