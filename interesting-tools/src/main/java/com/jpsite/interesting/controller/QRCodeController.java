package com.jpsite.interesting.controller;

import com.jpsite.interesting.util.FileConvertUtils;
import com.jpsite.interesting.util.QRCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 生成二维码请求类
 * @author jiangpeng
 * @date 2019/11/28 0028
 */
@Controller
@RequestMapping("qrCode")
public class QRCodeController {

    /**
     * 跳转页面
     * @return
     */
    @GetMapping
    public String qrCode(){
        return "qr_code";
    }

    /**
     * 生成二维码
     * @param content 内容
     * @param response HttpServletResponse
     */
    @PostMapping
    public void createQrCode(String content, @RequestParam("logoFile") MultipartFile logoFile, HttpServletResponse response) throws Exception {
        File file = !logoFile.isEmpty() ? FileConvertUtils.multipartFileToFile(logoFile): null;
        QRCodeUtils.QREncode(content, file, response.getOutputStream());
    }
}
