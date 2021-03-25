package com.example.gatewayredis.request;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author sa
 * @date 25.03.2021
 * @time 16:52
 */
@Service
public class RouterService
{
    private final RestTemplate restTemplate;

    public RouterService()
    {
        restTemplate = new RestTemplate();
    }

    public List<Placeholder> findAllPlaceholders()
    {
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<List<Placeholder>> routeResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Placeholder>>() {});
        return routeResponse.getBody();
    }

    public List<ApiRoute> findAllRoutes()
    {
        ApiRoute apiRoute1 = new ApiRoute("path_route", "/todos/**", "https://jsonplaceholder.typicode.com", 3);
        ApiRoute apiRoute2 = new ApiRoute("post_route_example", "/posts", "https://jsonplaceholder.typicode.com", 5);

        return Arrays.asList(apiRoute1, apiRoute2);
    }
}
