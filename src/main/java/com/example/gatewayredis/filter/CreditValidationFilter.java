package com.example.gatewayredis.filter;

import com.example.gatewayredis.service.ICreditService;
import com.example.gatewayredis.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

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
        List<String> apiKeys = request.getHeaders().get("Authorization");
        List<String> creditKeys = request.getHeaders().get("credit");

        if (CollectionUtils.isEmpty(apiKeys) || CollectionUtils.isEmpty(creditKeys))
        {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        Integer creditConsumer = Integer.valueOf(creditKeys.get(0));
        var credit = creditService.getCredit(apiKeys.get(0));

        if (!credit.isPresent())
        {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        else if (DateUtils.isUpToDate(credit.get().getExpirationTime()) && credit.get().getRemainingQuote() <= 0)
        {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }

        creditService.updateCredit(apiKeys.get(0), creditConsumer);
        return chain.filter(exchange);
    }
}
