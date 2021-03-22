package com.example.gatewayredis.service;

import com.example.gatewayredis.redis.CreditRedis;
import com.example.gatewayredis.repository.ICreditRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:30
 */
@Service
@RequiredArgsConstructor
public class CreditService implements ICreditService
{
    private final ICreditRedisRepository creditRedisRepository;

    @Override
    public void saveCredit(String apiKey, Integer quote)
    {
        CreditRedis credit = new CreditRedis(apiKey, quote);
        creditRedisRepository.save(credit);
    }

    @Override
    public Optional<CreditRedis> getCredit(String apiKey)
    {
        return creditRedisRepository.findById(apiKey);
    }
}
