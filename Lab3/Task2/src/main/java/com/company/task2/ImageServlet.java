package com.company.task2;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ImageServlet extends HttpServlet {

    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

    private static final int WIDTH = 640;
    private static final int HEIGHT = 120;

    private static final String TEXT = "Hello, World!";
    private static final String FONT_NAME = "Times New Roman";
    private static final int FONT_SIZE = 72;

    private static final int X = 100;
    private static final int Y = 100;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(IMAGE_CONTENT_TYPE);

        try (ServletOutputStream out = response.getOutputStream()) {
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            Graphics graphics = image.getGraphics();
            graphics.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
            Random random = new Random();
            graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            graphics.drawString(TEXT, X, Y);
            ImageIO.write(image, "jpeg", out);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
