package com.chenrj.zhihu.model;

import java.util.Date;

/**
 * @ClassName LoginTicket
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 8:25
 * @Version 1.0
 */
public class LoginTicket {

    private int id;
    private int userId;
    private Date expired;
    private String status;
    private String ticket;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getExpired() {
        return expired;
    }

    public String getStatus() {
        return status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                       "id=" + id +
                       ", userId=" + userId +
                       ", expired=" + expired +
                       ", status='" + status + '\'' +
                       ", ticket='" + ticket + '\'' +
                       '}';
    }
}
