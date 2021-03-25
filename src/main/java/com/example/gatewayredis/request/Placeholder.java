package com.example.gatewayredis.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sa
 * @date 25.03.2021
 * @time 16:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Placeholder
{
    private Long userId;
    private Long id;
    private String title;
    private String body;
}
