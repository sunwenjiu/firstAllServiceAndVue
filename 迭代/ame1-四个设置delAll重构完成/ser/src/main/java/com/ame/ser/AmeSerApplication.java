package com.ame.ser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmeSerApplication {

	private static Logger logger =  LoggerFactory.getLogger(AmeSerApplication.class);

	public static void main(String[] args) {
		logger.info("-------项目即将启动---------");
		SpringApplication.run(AmeSerApplication.class, args);
		logger.info("-------项目启动了---------");
	}

}
