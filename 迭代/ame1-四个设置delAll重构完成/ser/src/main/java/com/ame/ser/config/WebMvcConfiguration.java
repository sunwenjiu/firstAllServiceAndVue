package com.ame.ser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
* Description: 解决跨域问题
* Date: 2017-10-26
* Time: 16:36
*
* @author: ycbx
*/
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

   @Override
   public void addCorsMappings(CorsRegistry registry) {
       // 解决跨域问题
       registry.addMapping("/**")
               .allowedOrigins("*")
               .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
               .allowedHeaders("origin", "content-type", "accept", "x-requested-with", "sid", "Authorization")
               .exposedHeaders("access-control-allow-headers",
                       "access-control-allow-methods",
                       "access-control-allow-origin",
                       "access-control-max-age",
                       "X-Frame-Options")
               .allowCredentials(true).maxAge(10800);
       super.addCorsMappings(registry);
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       super.addInterceptors(registry);
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       super.configureMessageConverters(converters);
   }

}
