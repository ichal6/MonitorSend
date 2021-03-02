package com.lechowicz.monitorsend.controller;

import com.lechowicz.monitorsend.service.ScreenService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class ScreenController {
    final ScreenService screenService;
    final Image cursorImage;
    final Rectangle areaOfScreenshot;

    public ScreenController(ScreenService screenService) throws IOException {
        this.screenService = screenService;
        this.cursorImage = screenService.getCursor();
        this.areaOfScreenshot = screenService.getAreaOfScreenshot();
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedImage fullScreenImage = this.screenService.getFullScreen(this.areaOfScreenshot);

        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;

        fullScreenImage.getGraphics().drawImage(cursorImage, x, y, null);

        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(fullScreenImage, "jpg", byteArrayOutputStream);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArrayOutputStream.toByteArray(), response.getOutputStream());

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
