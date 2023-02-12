package br.com.woodenlion.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
class DataSourceConfig {

    @Bean("graceful")
    @ConfigurationProperties("graceful.datasource")
    fun gracefulDateSource(): DataSource {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("graceful.datasource")
    fun oracleJdbcTemplate(oracleDataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(oracleDataSource)
    }

}