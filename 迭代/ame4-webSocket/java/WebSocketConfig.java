package com.ame.ser.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author LSQ
 * @date 2019/7/10 9:31
 */
@Configuration
@Slf4j
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("------------------webSocket 配置成功------------------");
        return new ServerEndpointExporter();
    }

}
