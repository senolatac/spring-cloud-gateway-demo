package com.example.gatewayredis.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sa
 * @date 25.03.2021
 * @time 17:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRoute
{
    private String id;
    private String path;
    private String uri;
    private Integer credit;
}
