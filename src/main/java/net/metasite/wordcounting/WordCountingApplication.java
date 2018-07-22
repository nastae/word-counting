package net.metasite.wordcounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@ComponentScan({"net.metasite.wordcounting","net.metasite.wordcounting.controller"})
@SpringBootApplication
public class WordCountingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WordCountingApplication.class, args);
	}
	
}


