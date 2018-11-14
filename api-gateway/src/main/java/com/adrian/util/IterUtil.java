package com.adrian.util;

/**
 * @author asus
 * @ClassName IterUtil
 * @description TODO
 * @Date 2018/10/24 16:12
 * @Version 1.0v
 **/
public class IterUtil {

    /**
     * 以 conjunction 为分隔符将集合转换为字符串
     *
     * @param <T>         集合元素类型
     * @param iterable    {@link Iterable}
     * @param conjunction 分隔符
     * @return 连接后的字符串
     */
    public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
        if (null == iterable) {
            return null;
        }
        return join((Iterable<T>) iterable.iterator(), conjunction);
    }

}
