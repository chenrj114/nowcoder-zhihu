package com.chenrj.zhihu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName Question
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 21:04
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class Question {
    private int id;
    private int userId;
    private String title;
    private String content;
    private Date createDate;
    private Date updateDate;
    private int commentCount;
}
