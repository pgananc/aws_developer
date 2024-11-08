package com.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class App {

    @Value("${app.topic.alumnos}")
    private String topicAlumnos;

  

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    NewTopic alumnos() {
        return TopicBuilder.name(topicAlumnos)
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean("transactionManager")
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}