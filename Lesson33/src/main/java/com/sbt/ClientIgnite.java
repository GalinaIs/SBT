package com.sbt;

import com.sbt.entity.Company;
import com.sbt.entity.User;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

import java.time.LocalDate;

public class ClientIgnite {
    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start(Config.configuration(true))) {
            CacheConfiguration<String, User> userCacheConfiguration = new CacheConfiguration<>();
            userCacheConfiguration.setName("userCache");
            userCacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
            IgniteCache<String, User> userCache = ignite.getOrCreateCache(userCacheConfiguration);

            /*LocalDate date = LocalDate.of(2001, 9,9);
            Company company = new Company("Sberbank", "123456789");
            userCache.put("Galina", new User("Galina", date, company));
            userCache.put("Anna", new User("Anna", date, company));
            userCache.put("Elena", new User("Elena", date, company));*/

            System.out.println(userCache.get("Galina"));
        }
    }
}
