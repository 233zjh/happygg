package com.ruoyi.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ImageCompressor {

    public static void main(String[] args) {

        String basePath = "D:\\DOWNLOAD\\云相册\\高高";
        File dir = new File(basePath);
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                String path = file.getAbsolutePath().replace("\\","\\\\");
                File directory = new File(path);
                if (directory.exists() && directory.isDirectory()) {
                    System.getProperties().put("user.dir", path);
//                    将当前目录下的所有JPEG图像转换为GIF格式（注意：这会直接修改原文件）
                    String cmd = "mogrify -format HEIC *.JPG";
                    executeCmd(cmd);
                } else {
                    System.out.println("指定的目录不存在或不是一个有效的目录。");
                }
            }
        }


    }

    public static void executeCmd(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            process.waitFor();
        } catch (IOException e) {
            log.error("执行命令失败：" + cmd, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
