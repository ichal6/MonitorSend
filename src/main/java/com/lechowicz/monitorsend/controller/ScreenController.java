package com.lechowicz.monitorsend.controller;

import com.lechowicz.monitorsend.service.ScreenService;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(fullScreenImage, "png", byteArrayOutputStream);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArrayOutputStream.toByteArray(), response.getOutputStream());
    }
}
