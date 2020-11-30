package com.spt.cctest.datasource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("com.spt.cctest.datasource")
public class EmbeddedDataSourceConfiguration {
    public static final String DATA_SOURCE = "ccdataSource";
    public static final String CLASSPATH_DB_MIGRATION_SETUP_SQL = "classpath:db/setup.sql";
    public static final String PACKAGE_MODEL = "com.spt.cctest.datasource.model";

    @Bean(name = DATA_SOURCE)
    DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(CLASSPATH_DB_MIGRATION_SETUP_SQL)
                .setName(DATA_SOURCE)
                .build());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(PACKAGE_MODEL)
                .persistenceUnit(DATA_SOURCE)
                .build();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
