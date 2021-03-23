package com.example.gatewayredis;

import com.example.gatewayredis.redis.CreditPeriod;
import com.example.gatewayredis.service.ICreditService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class GatewayRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayRedisApplication.class, args);
	}

    @Bean
    ApplicationRunner init(ICreditService creditService) {
        return args -> Stream.of("1", "2", "3", "4").forEach(key -> {
            creditService.saveCredit(key, 10, CreditPeriod.SECONDS_30);
        });
    }

}
