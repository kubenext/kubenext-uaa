package com.github.kubenext.uaa.config;

import com.github.kubenext.config.SpringProfiles;
import com.github.kubenext.properties.CommonProperties;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.annotation.PreDestroy;

/**
 * 缓存配置
 * @author lishangjin
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);

    private final Environment env;

    public CacheConfiguration(Environment env) {
        this.env = env;
    }

    @PreDestroy
    public void destory() {
        logger.info("Closing cache manager");
        Hazelcast.shutdownAll();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        logger.debug("Starting HazelcastCacheManager");
        return new HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(CommonProperties properties) {
        logger.debug("Configuring Hazelcast");
        CommonProperties.Cache.Hazelcast hazelcastConfig = properties.getCache().getHazelcast();
        HazelcastInstance hazelcastInstance = Hazelcast.getHazelcastInstanceByName(hazelcastConfig.getInstanceName());
        if (hazelcastInstance != null) {
            logger.debug("Hazelcast already initialized");
            return hazelcastInstance;
        }
        Config config = new Config();
        config.setInstanceName(hazelcastConfig.getInstanceName());
        config.getNetworkConfig().setPort(hazelcastConfig.getPort());
        config.getNetworkConfig().setPortAutoIncrement(true);

        // In development, remove multicast auto-configuration
        if (env.acceptsProfiles(Profiles.of(SpringProfiles.SPRING_PROFILE_DEVELOPMENT))) {
            System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
            config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
        }

        config.getMapConfigs().put("default", initializeDefaultMapConfig(hazelcastConfig));
        config.setManagementCenterConfig(initializeDefaultManagementCenterConfig(hazelcastConfig));
        config.getMapConfigs().put(hazelcastConfig.getDefaultPackage(), initializeDomainMapConfig(hazelcastConfig));
        return Hazelcast.newHazelcastInstance(config);
    }

    private MapConfig initializeDefaultMapConfig(CommonProperties.Cache.Hazelcast hazelcastConfig) {
        MapConfig config = new MapConfig();
        config.setBackupCount(hazelcastConfig.getBackupCount());
        config.setEvictionPolicy(EvictionPolicy.LRU);
        config.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));
        return config;
    }

    private MapConfig initializeDomainMapConfig(CommonProperties.Cache.Hazelcast hazelcastConfig) {
        MapConfig config = new MapConfig();
        config.setTimeToLiveSeconds(hazelcastConfig.getTimeToLiveSeconds());
        return config;
    }

    private ManagementCenterConfig initializeDefaultManagementCenterConfig(CommonProperties.Cache.Hazelcast hazelcastConfig) {
        ManagementCenterConfig config = new ManagementCenterConfig();
        config.setEnabled(hazelcastConfig.getManagementCenter().isEnabled());
        config.setUrl(hazelcastConfig.getManagementCenter().getUrl());
        config.setUpdateInterval(hazelcastConfig.getManagementCenter().getUpdateInterval());
        return config;
    }



}
