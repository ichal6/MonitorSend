package com.lechowicz.monitorsend.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ScreenService {
    final Robot robot;

    static {
        System.setProperty("java.awt.headless", "false");
    }

    public ScreenService() throws AWTException {
        this.robot = new Robot();
    }

    public Image getCursor() throws IOException {
        var imgFile = getClass().getResourceAsStream("/cursors/arrow.gif");
        return ImageIO.read(imgFile);
    }

    public BufferedImage getFullScreen(Rectangle areaOfScreenshot){
        return this.robot.createScreenCapture(areaOfScreenshot);
    }

    public Rectangle getAreaOfScreenshot(){
        return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }
}
