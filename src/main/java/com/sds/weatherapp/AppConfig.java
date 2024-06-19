package com.sds.weatherapp;

import java.io.IOException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import com.sds.weatherapp.jwt.JwtUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import kr.co.shineware.nlp.komoran.core.Komoran;
@Configuration
public class AppConfig {
	
	@Bean
	public Komoran komoran() {
		
		String modelPath = null;
		
		try {
			modelPath = new ClassPathResource("model_light").getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Komoran(modelPath);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public JwtUtil jwtUtil() throws Exception {
		return new JwtUtil();
	}
	
	@Bean
	public ServletContextInitializer servletContextInitializer(JwtUtil jwtUtil) {
		return new ServletContextInitializer() {
			
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				servletContext.setAttribute("key", jwtUtil.getEncodedPublicKey());
			}
		};
	}
}
