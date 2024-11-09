package io.stitch.stitch.config;

import org.springframework.cache.CacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        // Short-lived cache configuration
        RedisCacheConfiguration shortCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues();

        // Long-lived cache configuration
        RedisCacheConfiguration longCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("shortCache", shortCacheConfig)
                .withCacheConfiguration("longCache", longCacheConfig)
                .build();
    }

}

