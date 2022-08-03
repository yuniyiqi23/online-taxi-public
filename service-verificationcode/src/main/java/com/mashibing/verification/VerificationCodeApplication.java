package com.mashibing.verification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VerificationCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerificationCodeApplication.class);
    }
}
