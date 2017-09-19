package com.test1;

import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableIgniteRepositories
@ComponentScan(basePackages = { "com.test1" })
public class SpringAppConfig {
	@Bean
	public Ignite igniteInstance() {
		// TcpDiscoverySpi spi = new TcpDiscoverySpi();
		// TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
		// ipFinder.setAddresses(Arrays.asList("host1", "host2"));
		// spi.setIpFinder(ipFinder);

		IgniteConfiguration cfg = new IgniteConfiguration();
		// cfg.setDiscoverySpi(spi);
		// Setting some custom name for the node.
		// cfg.setIgniteInstanceName("springDataNode");
		// Enabling peer-class loading feature.
		cfg.setPeerClassLoadingEnabled(true);
		// Defining and creating a new cache to be used by Ignite Spring Data
		// repository.
		CacheConfiguration ccfgDog = new CacheConfiguration("DogCache");
		CacheConfiguration ccfgBreed = new CacheConfiguration("BreedCache");

		// CacheConfiguration<Long, Person> cacheCfg = new CacheConfiguration<>();
		ccfgDog.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheJDBCDogStore.class));
		// ccfgDog.setCacheStoreFactory(FactoryBuilder.factoryOf(new
		// CacheJDBCDogStore()));
		ccfgDog.setReadThrough(true);
		ccfgDog.setWriteThrough(true);
		// Setting SQL schema for the cache.
		ccfgDog.setIndexedTypes(Long.class, Dog.class);

		ccfgBreed.setIndexedTypes(Long.class, Breed.class);
		cfg.setCacheConfiguration(new CacheConfiguration[] { ccfgDog, ccfgBreed });

		// Ignition.setClientMode(true);
		return Ignition.start(cfg);
	}
}
