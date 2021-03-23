package com.example.gatewayredis.service;

import com.example.gatewayredis.redis.CreditPeriod;
import com.example.gatewayredis.redis.CreditRedis;
import com.example.gatewayredis.repository.ICreditRedisRepository;
import com.example.gatewayredis.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public void saveCredit(String apiKey, Integer quote, CreditPeriod period)
    {
        CreditRedis credit = new CreditRedis(apiKey, quote, quote, period.expiration(), period.seconds());
        creditRedisRepository.save(credit);
    }

    @Transactional
    @Override
    public void updateCredit(String apiKey, int creditConsumer)
    {
        //Assumed not-null in update case.
        CreditRedis existCredit = getCredit(apiKey).orElse(null);
        Long currentExpirationTime = DateUtils.currentExpirationTime(existCredit.getExpirationTime(), existCredit.getPeriod());

        if (currentExpirationTime.compareTo(existCredit.getExpirationTime()) != 0)
        {
            existCredit.setRemainingQuote(existCredit.getMaxQuote());
        }

        existCredit.setExpirationTime(currentExpirationTime);
        existCredit.setRemainingQuote(existCredit.getRemainingQuote() - creditConsumer);
        creditRedisRepository.save(existCredit);
    }

    @Override
    public Optional<CreditRedis> getCredit(String apiKey)
    {
        return creditRedisRepository.findById(apiKey);
    }
}
