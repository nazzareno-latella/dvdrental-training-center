package it.nlatella.dvdrental.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"it.nlatella.dvdrental.data.repository" })
public class DatalayerConfig {

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.jdbc-url}")
	private String jdbcUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Primary
	@Bean
	public DataSource dataSource() {
		String databaseUrl = System.getenv("DATABASE_URL");
		if (databaseUrl != null) {
			URI dbUri;
			try {
				dbUri = new URI(System.getenv("DATABASE_URL"));
			} catch (URISyntaxException e) {
				return DataSourceBuilder.create().build();
			}

			username = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];
			jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		}

		return DataSourceBuilder.create().driverClassName(driverClassName).url(jdbcUrl).username(username)
				.password(password).build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("it.nlatella.dvdrental.data.entity")
				.persistenceUnit("dvdrental").build();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(final EntityManagerFactory customerEntityManager) {
		return new JpaTransactionManager(customerEntityManager);
	}

}
