package br.com.ravelino.letty.poc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author ravelino
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="br.com.ravelino.letty.poc")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
