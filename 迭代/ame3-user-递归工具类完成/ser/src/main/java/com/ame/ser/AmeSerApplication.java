package com.ame.ser;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
public class AmeSerApplication {


	public static void main(String[] args) {
		log.info("------------------项目开始启动------------------");
		SpringApplication.run(AmeSerApplication.class, args);
		log.info("------------------项目启动成功------------------");
	}

}
