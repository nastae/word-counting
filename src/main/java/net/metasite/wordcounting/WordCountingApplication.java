package net.metasite.wordcounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"net.metasite.wordcounting"})
@SpringBootApplication
public class WordCountingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WordCountingApplication.class, args);
	}
}