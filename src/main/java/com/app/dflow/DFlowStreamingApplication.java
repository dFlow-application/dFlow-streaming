package com.app.dflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//public class DFlowStreamingApplication extends SpringBootServletInitializer {
@EnableSwagger2
public class DFlowStreamingApplication {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(DFlowStreamingApplication.class);
//	}

	private static final Logger log = LoggerFactory.getLogger(DFlowStreamingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DFlowStreamingApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.app.dflow")).build();
	}
}
