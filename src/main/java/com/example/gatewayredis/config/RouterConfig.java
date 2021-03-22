package com.example.gatewayredis.config;

import com.example.gatewayredis.filter.CustomGatewayFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:23
 */
@Configuration
public class RouterConfig
{

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder)
    {
        return builder.routes()
                //localhost:8080/todos/1
                .route("path_route", r -> r.path("/todos/**")
                        .filters(f -> f.addRequestHeader("credit", "3")
                                .filter(new CustomGatewayFilter().apply(new CustomGatewayFilter.Config())))
                        .uri("https://jsonplaceholder.typicode.com"))
                //POST localhost:8080/posts
                //so there is not extra parameter for request-method (POST, GET...)
                .route("post_route_example", r -> r.path("/posts")
                        .filters(f -> f.addRequestHeader("credit", "5"))
                        .uri("https://jsonplaceholder.typicode.com"))
                //localhost:8080/get/1
                .route("rewrite_path_route", r -> r.path("/get/**")
                        .filters(f -> f.addRequestHeader("credit", "3")
                                .rewritePath("/get/(?<segment>.*)", "/todos/${segment}"))
                        .uri("https://jsonplaceholder.typicode.com"))
                //localhost:8080/anything/1
                //So if I visit it 5 consecutive times (using eg. Postman) the last 3 would return 429 - Too Many Requests
                .route("limit_route", r -> r.path("/anything/**")
                        .filters(f -> f.addRequestHeader("credit", "1")
                                .rewritePath("/anything/(?<segment>.*)", "/todos/${segment}")
                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())
                                        //Rate-limit should be based on a key
                                        .setKeyResolver(userKeyResolver())))
                        .uri("https://jsonplaceholder.typicode.com"))
                .build();
    }

    @Bean
    RedisRateLimiter redisRateLimiter()
    {
        //	 * @param defaultReplenishRate how many tokens per second in token-bucket algorithm.
        //	 * @param defaultBurstCapacity how many tokens the bucket can hold in token-bucket
        return new RedisRateLimiter(1, 2);
    }

    @Bean
    KeyResolver userKeyResolver()
    {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("Authorization"));
    }
}
