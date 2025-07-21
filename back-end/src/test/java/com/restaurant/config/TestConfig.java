package com.restaurant.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {
    
    /**
     * Provides an in-memory database for integration tests to avoid affecting the production database.
     * Can be used by adding the annotation @Import(TestConfig.class) to test classes that need it.
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema-test.sql")
                .addScript("classpath:data-test.sql")
                .build();
    }
}
