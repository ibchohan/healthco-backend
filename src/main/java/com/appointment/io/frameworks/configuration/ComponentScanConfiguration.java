package com.appointment.io.frameworks.configuration;


import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.appointment.*"})
@EnableJpaRepositories("com.appointment.*")
@EntityScan("com.appointment.*")
@Configuration
public class ComponentScanConfiguration {
}
