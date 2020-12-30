package sa.com.saib.dig.wlt.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, sa.com.saib.dig.wlt.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, sa.com.saib.dig.wlt.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, sa.com.saib.dig.wlt.domain.User.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Authority.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.User.class.getName() + ".authorities");
            createCache(cm, sa.com.saib.dig.wlt.domain.Customer.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Customer.class.getName() + ".fingerDetails");
            createCache(cm, sa.com.saib.dig.wlt.domain.Customer.class.getName() + ".wallets");
            createCache(cm, sa.com.saib.dig.wlt.domain.CustomerDetails.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.CustomerPreference.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Address.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.KYCInfo.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.FingerDetails.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Wallet.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Wallet.class.getName() + ".accountSchemes");
            createCache(cm, sa.com.saib.dig.wlt.domain.AccountScheme.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Transfer.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Beneficiary.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.PaymentDetails.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.BeneficiaryBank.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Debtor.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Creditor.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.Amount.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.TransactionInfo.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.TransactionAttribute.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.TransactionDetails.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.TransactionHistory.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.CalculationInfo.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.CalculationInfoDetails.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.ChargeAmount.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.DestinationChargeAmount.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.CurrencyList.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.BankList.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.CountryList.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.CountryCodeList.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.VersionList.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.BranchList.class.getName());
            createCache(cm, sa.com.saib.dig.wlt.domain.DistrictList.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
