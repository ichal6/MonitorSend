package com.lechowicz.monitorsend.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ScreenService {
    final Robot robot;

    static {
        System.setProperty("java.awt.headless", "false");
    }

    public ScreenService() throws AWTException {
        this.robot = new Robot();
    }

    public BufferedImage getFullScreen(){
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return this.robot.createScreenCapture(screenRect);
    }
}
