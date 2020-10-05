package com.chenrj.zhihu.util;

import java.util.Date;

/**
 * @ClassName TimeUtil
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 9:16
 * @Version 1.0
 */
public class TimeUtil {

    public static Date longToDate(long datetime) {
        return new Date(datetime);
    }
}
