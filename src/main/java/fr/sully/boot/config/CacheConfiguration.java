package fr.sully.boot.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(fr.sully.boot.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(fr.sully.boot.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Client.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Client.class.getName() + ".projets", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Projet.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Projet.class.getName() + ".numerosAffaires", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Projet.class.getName() + ".applications", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.NumeroAffaire.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Application.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Application.class.getName() + ".dependencies", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Application.class.getName() + ".outils", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Application.class.getName() + ".traitements", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Dependency.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Outil.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Traitement.class.getName(), jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.Traitement.class.getName() + ".donneesPersonnelles", jcacheConfiguration);
            cm.createCache(fr.sully.boot.domain.DonneePersonnelle.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
