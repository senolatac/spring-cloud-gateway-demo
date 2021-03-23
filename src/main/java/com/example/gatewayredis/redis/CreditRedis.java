package com.example.gatewayredis.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "ApiKey")
public class CreditRedis
{
    @Id
    private String id;//apiKey
    private Integer maxQuote;
    private Integer remainingQuote;
    private Long expirationTime;//Don't use time-to-live, we need it always
    private Long period;//Refresh it on every day, every hour...
}
