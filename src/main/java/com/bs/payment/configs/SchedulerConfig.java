package com.bs.payment.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

@Configuration
public class SchedulerConfig {
    @Bean
    public SchedulerClient schedulerClient() {

        return SchedulerClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
