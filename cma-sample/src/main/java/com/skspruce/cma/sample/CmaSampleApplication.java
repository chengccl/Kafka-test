package com.skspruce.cma.sample;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CmaSampleApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(CmaSampleApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}
}
