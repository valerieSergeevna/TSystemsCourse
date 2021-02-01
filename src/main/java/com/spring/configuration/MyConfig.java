package com.spring.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import com.mchange.v2.c3p0.DataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.spring.webapp")
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "com.spring.webapp.dao.securityDAO"
})
//@EntityScan(basePackages =  "com.spring.webapp.entity")
public class MyConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/reha_db?useSSL=false&amp;serverTimezone=UTC");
            dataSource.setUser("postgres");
            dataSource.setPassword("postgres");

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

      @Bean(name="entityManagerFactory")
      public LocalSessionFactoryBean sessionFactory(){
          LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
          sessionFactory.setDataSource(dataSource());
          sessionFactory.setPackagesToScan("com.spring.webapp.entity");

          Properties hibernateProperties = new Properties();
          hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");

          hibernateProperties.setProperty("hibernate.show_sql", "true");
        //  hibernateProperties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
          hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
          sessionFactory.setHibernateProperties(hibernateProperties);
          return sessionFactory;
      }

   /* @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.spring.webapp.dao");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }*/

   @Bean
      public HibernateTransactionManager transactionManager(){
          HibernateTransactionManager transactionManager = new HibernateTransactionManager();
          transactionManager.setSessionFactory(sessionFactory().getObject());
          return transactionManager;
      }
   /* @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }*/

    @Bean
    public PhysicalNamingStrategy physical() {
        return new PhysicalNamingStrategyStandardImpl();
    }

    @Bean
    public ImplicitNamingStrategy implicit() {
        return new ImplicitNamingStrategyLegacyJpaImpl();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }


}
