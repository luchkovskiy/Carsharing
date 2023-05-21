package com.luchkovskiy.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${caffeineconfig.initialCapacity}")
    private int initialCapacity;

    @Value("${caffeineconfig.maximumSize}")
    private long maximumSize;

    @Value("${caffeineconfig.expireAfterAccess}")
    private long expireAfterAccess;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("roles", "carClasses", "subscriptionLevels");
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }

    // TODO: 21.05.2023 Заполнить exception, создать свои

}
