package com.jiezhan.auth.utils;

import com.jiezhan.auth.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author: zp
 * @Date: 2019-12-02 16:37
 * @Description:
 */
public class RandomCodeUtil {

    /**
     * 随机产生数字与字母组合的字符串
     */
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 图片宽
     */
    private int width = 95;
    /**
     * 图片高
     */
    private int height = 25;
    /**
     * 干扰线数量
     */
    private int lineSize = 40;
    /**
     * 随机产生字符数量
     */

    private int stringNum = 4;

    private static final Logger logger = LoggerFactory.getLogger(RandomCodeUtil.class);

    private Random random = new Random();

    /**
     * 获得字体
     */
    private Font getFont() {
        return new Font("Fixedsys", Font.BOLD, 18);
    }

    /**
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc) {
        fc = fc > 255? 255:fc;
        bc = bc > 255? 255:bc;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 生成随机图片
     */
    public String getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        //图片大小
        g.fillRect(0, 0, width, height);
        //字体大小
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        //字体颜色
        g.setColor(getRandColor(110, 133));

        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drawLine(g);
        }

        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drawString(g, randomString, i);
        }
        logger.info(randomString);

        //将生成的随机字符串保存到session中
        session.removeAttribute(Constant.RANDOM_CODE_KEY);
        session.setAttribute(Constant.RANDOM_CODE_KEY, randomString);
        g.dispose();

        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", e);
        }
        return randomString;

    }

    /**
     * 绘制字符串
     */
    private String drawString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = getRandomString(random.nextInt(randString.length()));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     */
    private String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }

}
