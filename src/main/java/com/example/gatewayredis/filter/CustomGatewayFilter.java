package com.example.gatewayredis.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @author sa
 * @date 22.03.2021
 * @time 17:18
 */
@Slf4j
@Order(2)
@Component
public class CustomGatewayFilter extends AbstractGatewayFilterFactory<CustomGatewayFilter.Config>
{
    @Override
    public GatewayFilter apply(Config config)
    {
        return (exchange, chain) -> {
            Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
            String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
            log.info("Incoming request " + originalUri + " is routed to id: " + route.getId() + ", uri:" + routeUri);
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        private String template;
    }
}
