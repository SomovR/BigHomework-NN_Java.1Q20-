package com.application.utils;

import com.application.dao.*;
import com.application.model.Hall;
import com.application.model.Movie;
import com.application.model.Ticket;
import com.application.model.Visitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Objects;
import java.util.Properties;


@Configuration
public class Config {
    private final Environment environment;

    public Config(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public EntityManager entityManager() {
        return Objects.requireNonNull(entityManagerFactory().getObject()).createEntityManager();
    }

    @Bean
    public Dao<Hall> hallDao(EntityManagerFactory entityManagerFactory) {
        return new HallDaoImpl(entityManagerFactory);
    }

    @Bean
    public Dao<Movie> movieDao(EntityManagerFactory entityManagerFactory) {
        return new MovieDaoImpl(entityManagerFactory);
    }

    @Bean
    public Dao<Ticket> ticketDao(EntityManagerFactory entityManagerFactory) {
        return new TicketDaoImpl(entityManagerFactory);
    }

    @Bean
    public Dao<Visitor> visitorDao(EntityManagerFactory entityManagerFactory) {
        return new VisitorDaoImpl(entityManagerFactory);
    }

//    @Bean
//    public AppService<Hall> hallService(Dao<Hall> hallDao) {
//        return new HallService(hallDao);
//    }
//
//    @Bean
//    public AppService<Movie> movieService(Dao<Movie> movieDao) {
//        return new MovieService(movieDao);
//    }
//
//    @Bean
//    public AppService<Ticket> ticketService(Dao<Ticket> ticketDao) {
//        return new TicketService(ticketDao);
//    }
//
//    @Bean
//    public AppService<Visitor> visitorService(Dao<Visitor> visitorDao) {
//        return new VisitorService(visitorDao);
//    }

    @Bean
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory.setPackagesToScan("com.application");
        entityManagerFactory.setJpaProperties(hibernateJpaProperties());
        return entityManagerFactory;
    }

    private Properties hibernateJpaProperties() {
        Properties props = new Properties();
        props.put(org.hibernate.cfg.Environment.DRIVER, environment.getProperty("spring.datasource.driverClassName"));
        props.put(org.hibernate.cfg.Environment.URL, environment.getProperty("spring.datasource.url"));
        props.put(org.hibernate.cfg.Environment.SHOW_SQL, environment.getProperty("spring.jpa.show-sql"));
        props.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        props.put(org.hibernate.cfg.Environment.USER, environment.getProperty("spring.datasource.username"));
        props.put(org.hibernate.cfg.Environment.PASS, environment.getProperty("spring.datasource.password"));
        return props;
    }
}
