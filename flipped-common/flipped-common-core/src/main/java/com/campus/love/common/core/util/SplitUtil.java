package com.campus.love.common.core.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用来切割
 */
public class SplitUtil {

    public static<T> List<T> split(String str, String splitChar,Class<T> tClass) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return Arrays.stream(str.split(splitChar))
                .map(m->(T)m)
                .collect(Collectors.toList());
    }

    public static<T> List<T> split(String str,Class<T> tClass) {

        return split(str, ",", tClass);
    }
}
