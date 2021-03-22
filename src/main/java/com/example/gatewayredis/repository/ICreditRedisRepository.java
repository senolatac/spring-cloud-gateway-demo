package com.example.gatewayredis.repository;

import com.example.gatewayredis.redis.CreditRedis;
import org.springframework.data.repository.CrudRepository;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:29
 */
public interface ICreditRedisRepository extends CrudRepository<CreditRedis, String>
{
}
