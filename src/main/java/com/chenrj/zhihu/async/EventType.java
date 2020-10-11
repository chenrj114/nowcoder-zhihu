package com.chenrj.zhihu.async;

/**
 * @author rjchen
 * @date 2020/10/11
 */

public enum EventType {

    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private final int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
