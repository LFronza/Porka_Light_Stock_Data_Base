package com.porkalite.sdb.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.porkalite.sdb.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Produto.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Produto.class.getName() + ".saidas", jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Produto.class.getName() + ".ltoes", jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Saida.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Apresentacao.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Apresentacao.class.getName() + ".produtos", jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Grupo.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Grupo.class.getName() + ".produtos", jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Lote.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Fornecedor.class.getName(), jcacheConfiguration);
            cm.createCache(com.porkalite.sdb.domain.Fornecedor.class.getName() + ".lotes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
