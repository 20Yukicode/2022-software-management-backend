package com.campus.love.common.core.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用来切割
 */
public class SplitUtil {

    public static List<Integer> splitToInt(String str,String splitChar){
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return Arrays.stream(str.split(splitChar))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public static List<Integer> splitToInt(String str) {
        return splitToInt(str, ",");
    }
}
