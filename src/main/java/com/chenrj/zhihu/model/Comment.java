package com.chenrj.zhihu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName Comment
 * @Description
 * @Author rjchen
 * @Date 2020-05-06 16:32
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class Comment {

    private int id;
    private int userId;
    private int entityId;
    private CommentedType entityType;
    private String content;
    private Date createDate;
    private int likeCount;
    private int dislikeCount;

    public enum CommentedType {
        QUESTION,
        COMMENT;
    }
}
