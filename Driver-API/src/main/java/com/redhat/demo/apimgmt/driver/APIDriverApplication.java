package com.redhat.demo.apimgmt.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:application-default.properties")
public class APIDriverApplication {

	@Value("${app.name}")
	private String appName;
	
	@Value("${app.base.url}")
	private String appBaseUrl;
	
	@Value("${app.user.key}")
	private String appUserKey;
	
	@Value("${app.customer.min}")
	private int appCustomerMin;
	
	@Value("${app.customer.max}")
	private int appCustomerMax;
	
	@Value("${app.key.lookup.percent}")
	private int appKeyLookupPercent;
	
	@Value("${app.version.v1}")
	private int appVersionV1;
	
	@Value("${app.iterations}")
	private int appIterations;
	
	@Value("${app.sleep.min}")
	private int appSleepMin;
	
	@Value("${app.sleep.max}")
	private int appSleepMax;
	
	
	private static final Logger log = LoggerFactory.getLogger(APIDriverApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(APIDriverApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			
			APIProperties a = new APIProperties(appName,  appBaseUrl,  appUserKey,  
					appCustomerMin,  appCustomerMax, appKeyLookupPercent,  appVersionV1,  
					appIterations,  appSleepMin,  appSleepMax);
			
			TrafficDriver td = new TrafficDriver();
			td.setA(a);
			td.setRestTemplate(restTemplate);
			System.err.println("********************");
			td.drive();
			System.err.println("********************");
			/*
			String n = td.driveTraffic(restTemplate);
			System.err.println(n);*/
			//td.driveTraffic(restTemplate);
			
			//log.info(customer.toString());
		};
	}
	

}
