package com.example.gatewayredis.controller;

import com.example.gatewayredis.dto.CreditDto;
import com.example.gatewayredis.redis.CreditPeriod;
import com.example.gatewayredis.service.ICreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sa
 * @date 25.03.2021
 * @time 17:50
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController
{
    private final ICreditService creditService;

    @PostMapping
    public ResponseEntity<?> saveNewApiKey(@RequestBody CreditDto creditDto)
    {
        creditService.saveCredit(creditDto.getApiKey(), creditDto.getQuota(), CreditPeriod.DAYS_1);
        return ResponseEntity.ok(true);
    }
}
