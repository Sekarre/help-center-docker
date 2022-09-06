package com.sekarre.helpcentergateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HelpCenterGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpCenterGatewayApplication.class, args);
    }

}
