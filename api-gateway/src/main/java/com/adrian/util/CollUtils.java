package com.adrian.util;

import java.util.Iterator;

/**
 * @author asus
 * @ClassName CollUtils
 * @description TODO
 * @Date 2018/10/24 16:10
 * @Version 1.0v
 **/
public class CollUtils {
    /**
     * 以 conjunction 为分隔符将集合转换为字符串<br>
     * 如果集合元素为数组、{@link Iterable}或{@link Iterator}，则递归组合其为字符串
     *
     * @param <T> 集合元素类型
     * @param iterable {@link Iterable}
     * @param conjunction 分隔符
     * @return 连接后的字符串
     * @see IterUtil#join(Iterable, CharSequence)
     */
    public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
        return IterUtil.join(iterable, conjunction);
    }

}
