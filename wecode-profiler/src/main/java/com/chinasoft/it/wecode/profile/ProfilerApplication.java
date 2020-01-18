package com.chinasoft.it.wecode.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProfilerApplication {

    private static final Logger log = LoggerFactory.getLogger(ProfilerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProfilerApplication.class, args);
    }

    @GetMapping("/api/hello")
    public String hello() throws Exception {
        log.info("call hello");
        Thread.sleep(100);
        return "hello world";
    }

}
