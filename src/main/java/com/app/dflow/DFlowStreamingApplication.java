package com.app.dflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DFlowStreamingApplication extends SpringBootServletInitializer {

//public class DFlowStreamingApplication {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DFlowStreamingApplication.class);
	}
	private static final Logger log = LoggerFactory.getLogger(DFlowStreamingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DFlowStreamingApplication.class, args);
	}

}
