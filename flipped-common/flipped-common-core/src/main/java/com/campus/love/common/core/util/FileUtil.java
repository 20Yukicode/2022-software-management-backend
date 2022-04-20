package com.campus.love.common.core.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class FileUtil {

    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String bucketName;

    static {
        //读取yml配置文件，获得appId和appSecret
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("flipped-common/flipped-common-core/src/main/resources/application-key.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String,Object> map;
        Yaml yaml = new Yaml();
        map = yaml.load(inputStream);
        Map<String,String> ossMap = (Map)map.get("oss");
        endpoint = ossMap.get("endpoint").toString();
        accessKeyId = ossMap.get("accessKeyId").toString();
        accessKeySecret = ossMap.get("accessKeySecret").toString();
        bucketName = ossMap.get("bucketName").toString();

        //  to be continued
    }

    public static void saveFile(MultipartFile multipartFile, String filePath) {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        if (multipartFile != null) {
            try {
                InputStream inputstream = multipartFile.getInputStream();
                ossClient.putObject(bucketName, filePath, inputstream);
                log.info("save Avatar: "+ filePath);
            } catch (OSSException oe) {
                log.info("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                log.info("Error Message:" + oe.getErrorMessage());
                log.info("Error Code:" + oe.getErrorCode());
                log.info("Request ID:" + oe.getRequestId());
                log.info("Host ID:" + oe.getHostId());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }
    }

    public static void deleteFile(String fileUrl) {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        if (fileUrl != null) {
            try {
                //文件路径+文件名
                //String fileName = fileName + "/" + path;
                // 删除文件。
                log.info("delete Avatar: "+ fileUrl);
                ossClient.deleteObject(bucketName, fileUrl);
            } catch (OSSException oe) {
                log.info("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                log.info("Error Message:" + oe.getErrorMessage());
                log.info("Error Code:" + oe.getErrorCode());
                log.info("Request ID:" + oe.getRequestId());
                log.info("Host ID:" + oe.getHostId());
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }
    }

}