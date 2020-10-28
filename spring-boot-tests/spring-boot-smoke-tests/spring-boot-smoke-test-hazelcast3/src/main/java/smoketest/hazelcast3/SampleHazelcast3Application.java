package smoketest.hazelcast3;

import com.hazelcast.spring.cache.HazelcastCacheManager;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class SampleHazelcast3Application {

	public static void main(String[] args) {
		SpringApplication.run(SampleHazelcast3Application.class, args);
	}

	@Bean
	public ApplicationRunner registerCache(CountryRepository repository, HazelcastCacheManager cacheManager,
			CacheMetricsRegistrar registrar) {
		return (args) -> {
			repository.findByCode("BE");
			registrar.bindCacheToRegistry(cacheManager.getCache("countries"));
		};
	}

}
