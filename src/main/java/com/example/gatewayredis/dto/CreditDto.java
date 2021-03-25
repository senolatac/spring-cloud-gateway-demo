package com.example.gatewayredis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author sa
 * @date 25.03.2021
 * @time 17:55
 */
@Getter
@NoArgsConstructor
public class CreditDto
{
    private String apiKey;
    private Integer quota;
}
