package com.lechowicz.monitorsend.controller;

import com.lechowicz.monitorsend.service.ScreenService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class ScreenController {
    final ScreenService screenService;

    public ScreenController(ScreenService screenService){
        this.screenService = screenService;
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response) throws IOException {

        BufferedImage fullScreenImage = this.screenService.getFullScreen();

        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;

        ClassPathResource imgFile = new ClassPathResource("cursors/arrow.gif");
        Image cursor = ImageIO.read(imgFile.getFile());
        fullScreenImage.getGraphics().drawImage(cursor, x, y, null);

        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(fullScreenImage, "png", byteArrayOutputStream);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArrayOutputStream.toByteArray(), response.getOutputStream());
    }
}
