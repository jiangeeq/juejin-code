package com.jpsite.interesting.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具类
 * @author jiangpeng
 * @date 2019/11/28 0028
 */
@Slf4j
public class QRCodeUtils {
    /**
     * 生成二维码
     *
     * @Param Content 二维码内容
     * @Param outputStream
     */
    public static void QREncode(String content, File logoFile, OutputStream outputStream) throws WriterException,
            IOException {
        // 图像宽度
        int width = 200;
        // 图像高度
        int height = 200;
        // 图像类型
        String format = "png";
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //内容编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        /*
            问题：生成二维码正常,生成带logo的二维码logo变成黑白;  原因：MatrixToImageConfig默认黑白，需要设置BLACK、WHITE
            解决：https://ququjioulai.iteye.com/blog/2254382
         */
        if (logoFile != null) {
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
            BufferedImage bufferedImage = LogoMatrix(MatrixToImageWriter.toBufferedImage(bitMatrix,
                    matrixToImageConfig),
                    logoFile);
            //输出带logo图片
            ImageIO.write(bufferedImage, format, outputStream);
        } else {
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
        }
        log.info("二维码生成成功！");
    }

    /**
     * 识别二维码
     */
    public static void QRReader(File file) throws IOException, NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        //读取指定的二维码文件
        BufferedImage bufferedImage = ImageIO.read(file);
        BinaryBitmap binaryBitmap =
                new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        //定义二维码参数
        Map<DecodeHintType, String> hints = new HashMap<>(8);
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = formatReader.decode(binaryBitmap, hints);
        //输出相关的二维码信息
        log.info("解析结果：" + result.toString());
        log.info("二维码格式类型：" + result.getBarcodeFormat());
        log.info("二维码文本内容：" + result.getText());
        bufferedImage.flush();
    }

    /**
     * 二维码添加logo
     *
     * @param matrixImage 源二维码图片
     * @param logoFile    logo图片
     * @return 返回带有logo的二维码图片
     */
    public static BufferedImage LogoMatrix(BufferedImage matrixImage, File logoFile) throws IOException {
        /*
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeight = matrixImage.getHeight();
        /*
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(logoFile);

        int logoWidth = matrixWidth / 4;
        int logoHeight = matrixHeight / 4;

        int x = matrixWidth / 10 * 4;
        int y = matrixHeight / 10 * 4;

        //开始绘制图片
        g2.drawImage(logo, x, y, logoWidth, logoHeight, null);
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke);
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 20, 20);
        g2.setColor(Color.white);
        // 绘制圆弧矩形
        g2.draw(round);
        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke2);
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(x + 2, y + 2, logoWidth - 4, logoHeight - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        // 绘制圆弧矩形
        g2.draw(round2);

        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }
}
