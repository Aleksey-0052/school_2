package hogwarts.school_2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final static Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int port;


    @GetMapping("/port")
    public int getNumberPort() {
        logger.info("Getting port {}", port);
        return port;
    }





}