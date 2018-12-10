package com.khesl.ftploader.FtpLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan()
@EnableAutoConfiguration
//@SpringBootApplication
public class FtpLoaderApplication {

	public static void main(String[] args) {
		/*ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml", "db.xml");
		ctx.refresh();*/
		SpringApplication.run(FtpLoaderApplication.class, args);
	}
}



