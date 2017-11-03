package com.skspruce.ac.sample;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AcSampleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(AcSampleApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
