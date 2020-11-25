package com.chenrj.zhihu.model;

import java.util.Date;

public class Feed {
    private Integer id;

    private Date createdDate;

    private Integer userId;

    private String data;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public Feed setId(Integer id) {
        this.id = id;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Feed setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Feed setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getData() {
        return data;
    }

    public Feed setData(String data) {
        this.data = data == null ? null : data.trim();
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Feed setType(Integer type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", userId=").append(userId);
        sb.append(", data=").append(data);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }
}