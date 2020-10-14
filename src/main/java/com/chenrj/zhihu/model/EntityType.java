package com.chenrj.zhihu.model;

/**
 * @author rjchen
 * @date 2020/10/13
 */

public enum EntityType {

    USER(1),
    QUESTION(2),
    COMMENT(3);

    private final int code;

    EntityType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
