package com.fleta.watchingservice.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Duration;

@Slf4j
@Configuration
@EnableCaching
public class DefaultRedisCacheConfiguration extends CachingConfigurerSupport {

	@Value("${spring.redis.port:6379}")
	private int redisPort;

	@Value("${spring.redis.host:localhost}")
	private String redisHostName;

	@Value("${spring.redis.password:}")
	private String redisPassword;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
		redisConf.setHostName(redisHostName);
		redisConf.setPort(redisPort);
		redisConf.setPassword(redisPassword);
		return new LettuceConnectionFactory(redisConf);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
				.serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
				.build();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
				new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		// This item must be configured, otherwise it will report java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
		// Refer to: https://stackoverflow.com/questions/49016372/spring-data-redis-cacheable-java-lang-classcastexception-java-util-linkedhashm
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}

	@Bean
	@Primary
	public RedisCacheManager cacheManager() {
		// Initialize a RedisCacheWriter output stream
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory());
		// Using Jackson2JsonRedisSerializer serialization mechanism
		// Create a RedisSerializationContext.SerializationPair given adapter pair
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer());
		// Create CacheConfig
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(pair)
				.entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC))
				.disableCachingNullValues();
		return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
	}

	@Bean
	public RedisTemplate<String, Serializable> redisTemplateFactory() {
		final RedisTemplate<String, Serializable> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jackson2JsonRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer());
		template.afterPropertiesSet();

		log.info("Wiring up RedisTemplate...");

		return template;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			public Object generate(Object target, Method method, Object... params) {
				if (params.length == 0) {
					return "redis_" + method.getName();
				}
				if (params.length == 1) {
					Object param = params[0];
					if (param != null && !param.getClass().isArray()) {
						return "redis_" + method.getName() + "_" + param;
					}
				}
				return "redis_" + method.getName() + " [" + StringUtils.arrayToCommaDelimitedString(params) + "]";
			}
		};
	}
}
