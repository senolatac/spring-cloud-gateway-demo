package com.example.gatewayredis.filter;

import com.example.gatewayredis.service.ICreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory.Config;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:39
 */
@Order(1)
@Component
@RequiredArgsConstructor
public class CreditValidationFilter implements GlobalFilter
{
    private final ICreditService creditService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        String apiKey = request.getHeaders().getFirst("Authorization");
        Integer creditConsumer = Integer.valueOf(request.getHeaders().getFirst("credit"));
        var credit = creditService.getCredit(apiKey);
        if (!credit.isPresent())
        {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        else if (credit.get().getRemainingQuote() <= 0)
        {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
        creditService.saveCredit(apiKey, credit.get().getRemainingQuote() - creditConsumer);
        return chain.filter(exchange);
    }
}
