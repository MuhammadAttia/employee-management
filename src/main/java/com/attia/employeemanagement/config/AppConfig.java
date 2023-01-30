package com.attia.employeemanagement.config;

import com.attia.employeemanagement.statemachine.StateMachineEvents;
import com.attia.employeemanagement.statemachine.StateMachineStates;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
@EnableJpaRepositories({"org.springframework.statemachine.data.jpa","com.attia.employeemanagement.repository"})
@EntityScan({"org.springframework.statemachine.data.jpa","com.attia.employeemanagement.model"})
public class AppConfig {

    @Bean
    ObjectMapper defaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper;
    }

    @Bean
    public StateMachineService<StateMachineStates, StateMachineEvents> stateMachineService(
            final StateMachineFactory<StateMachineStates, StateMachineEvents> stateMachineFactory,
            final StateMachinePersist<StateMachineStates, StateMachineEvents, String> stateMachinePersist) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachinePersist);
    }

    @Bean
    public JpaPersistingStateMachineInterceptor<StateMachineStates, StateMachineEvents, String>
    jpaPersistingStateMachineInterceptor(final JpaStateMachineRepository stateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(stateMachineRepository);
    }
}
