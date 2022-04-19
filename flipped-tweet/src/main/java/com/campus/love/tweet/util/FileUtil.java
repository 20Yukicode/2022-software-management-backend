package com.campus.love.tweet.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static String getUrls(List<MultipartFile> files){

        files.parallelStream()
                .forEach(item->{
                    String filename = item.getOriginalFilename();
                });
        return null;
    }


    public static String getUrls(MultipartFile...files) {

        Arrays.stream(files).parallel()
                .forEach(item -> {
                    String filename = item.getOriginalFilename();
                });

        return null;
    }
}
