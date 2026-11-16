package com.hasan.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

	@Bean
	CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

		RedisCacheConfiguration defaults = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5))
				.disableCachingNullValues()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new JdkSerializationRedisSerializer()));

		Map<String, RedisCacheConfiguration> caches = new HashMap<>();

		caches.put("accountDetails", defaults.entryTtl(Duration.ofMinutes(5)));

		caches.put("accountList", defaults.entryTtl(Duration.ofMinutes(2)));

		caches.put("accountsByStatus", defaults.entryTtl(Duration.ofMinutes(2)));

		caches.put("accountsByCustomer", defaults.entryTtl(Duration.ofMinutes(2)));

		return RedisCacheManager.builder(connectionFactory).cacheDefaults(defaults)
				.withInitialCacheConfigurations(caches).build();
	}
}
