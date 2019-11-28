package com.jpsite.interesting.util;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 文件类型转换工具类
 * @author jiangpeng
 * @date 2019/11/1515:51
 */
public class FileConvertUtils {
    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     * @return File
     */
    public static File multipartFileToFile(@RequestParam MultipartFile file) throws Exception {
        File toFile = null;

        if (file == null || file.getSize() <= 0) {
            Assert.isNull(file, "The file does not exist!");
        } else {
            InputStream ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    private static void inputStreamToFile(InputStream ins, File file) {
        int byteSize = 8192;
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[byteSize];
            while ((bytesRead = ins.read(buffer, 0, byteSize)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
