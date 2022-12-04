package net.mujoriwi.walletind.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// setup cross origin as global
  @Configuration
  @EnableWebMvc
  public class CustomCorsOrigin implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
      // TODO Auto-generated method stub
      registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("http://localhost:3000");
    }

  }
