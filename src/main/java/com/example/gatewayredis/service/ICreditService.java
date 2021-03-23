package com.example.gatewayredis.service;

import com.example.gatewayredis.redis.CreditPeriod;
import com.example.gatewayredis.redis.CreditRedis;

import java.util.Optional;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:29
 */
public interface ICreditService
{
    void saveCredit(String apiKey, Integer quote, CreditPeriod period);

    void updateCredit(String apiKey, int creditConsumer);

    Optional<CreditRedis> getCredit(String apiKey);
}
