package com.uaer.app.Config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

//@Configuration
//@EnableJpaRepositories(
//    basePackages = ["com.uaer.app.Database.Repository"]
//)
class DataBaseConfig {

    //Oracle
//    @Bean(name = ["oracleDatasourceProperties"])
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    fun datasourceProperties():DataSourceProperties{
//        return DataSourceProperties()
//    }
//
//    @Primary
//    @Bean(name = ["oracleDatasource"])
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    fun oracleDatasource():DataSource{
//        return datasourceProperties().initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
//    }
//
//    @Primary
//    @Bean(name = ["oracleJdbcTemplate"])
//    fun jdbcTamplate(@Qualifier("oracleDatasource") dataSource: DataSource):NamedParameterJdbcTemplate{
//        return NamedParameterJdbcTemplate(dataSource)
//    }

    //Postage
//    @Bean(name = ["postgresDatasourceProperties"])
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    fun pdatasourceProperties(): DataSourceProperties {
//        return DataSourceProperties()
//    }
//
//    @Primary
//    @Bean(name = ["postgresDatasource"])
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    fun postgresDatasource(): DataSource {
//        return pdatasourceProperties().initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
//    }
//
//    @Primary
//    @Bean(name = ["postgresJdbcTemplate"])
//    fun jdbcTemplate(@Qualifier("postgresDatasource") dataSource: DataSource): NamedParameterJdbcTemplate {
//        return NamedParameterJdbcTemplate(dataSource)
//    }
}