package com.attia.employeemanagement.statemachine;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<StateMachineStates, StateMachineEvents> {

    private final JpaPersistingStateMachineInterceptor<StateMachineStates, StateMachineEvents, String> persister;

    public StateMachineConfiguration(JpaPersistingStateMachineInterceptor<StateMachineStates, StateMachineEvents, String> persister) {
        this.persister = persister;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<StateMachineStates, StateMachineEvents> config)
            throws Exception {
        config
                .withPersistence()
                .runtimePersister(persister);
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }


    @Override
    public void configure(StateMachineStateConfigurer<StateMachineStates, StateMachineEvents> states) throws Exception {
        states
                .withStates()
                .initial(StateMachineStates.ADDED)
                .fork(StateMachineStates.IN_CHECK)
                .join(StateMachineStates.ALL_CHECK_FINISHED)
                .state(StateMachineStates.APPROVED)
                .end(StateMachineStates.ACTIVE)
                .and()
                .withStates()
                .parent(StateMachineStates.IN_CHECK)
                .region("r1")
                .initial(StateMachineStates.WORK_PERMIT_CHECK_STARTED)
                .end(StateMachineStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION)
                .and()
                .withStates()
                .parent(StateMachineStates.IN_CHECK)
                .region("r1")
                .initial(StateMachineStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION)
                .end(StateMachineStates.WORK_PERMIT_CHECK_FINISHED)
                .and()
                .withStates()
                .parent(StateMachineStates.IN_CHECK)
                .initial(StateMachineStates.SECURITY_CHECK_STARTED)
                .end(StateMachineStates.SECURITY_CHECK_FINISHED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StateMachineStates, StateMachineEvents> transitions) throws Exception {
        transitions.withExternal()
                .source(StateMachineStates.ADDED).target(StateMachineStates.IN_CHECK).event(StateMachineEvents.BEGIN_CHECK)
                .and().withExternal()
                .source(StateMachineStates.WORK_PERMIT_CHECK_STARTED).target(StateMachineStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION).event(StateMachineEvents.COMPLETE_INITIAL_WORK_PERMIT_CHECK)
                .and().withExternal()
                .source(StateMachineStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION).target(StateMachineStates.WORK_PERMIT_CHECK_FINISHED).event(StateMachineEvents.FINISH_WORK_PERMIT_CHECK)
                .and().withExternal()
                .source(StateMachineStates.SECURITY_CHECK_STARTED).target(StateMachineStates.SECURITY_CHECK_FINISHED).event(StateMachineEvents.FINISH_SECURITY_CHECK)
                .and()
                .withExternal()
                .source(StateMachineStates.ALL_CHECK_FINISHED).target(StateMachineStates.APPROVED)
                .and()
                .withExternal()
                .source(StateMachineStates.APPROVED).target(StateMachineStates.ACTIVE).event(StateMachineEvents.ACTIVATE)
                .and()
                .withFork()
                .source(StateMachineStates.IN_CHECK)
                .target(StateMachineStates.WORK_PERMIT_CHECK_STARTED)
                .target(StateMachineStates.SECURITY_CHECK_STARTED)
                .and()
                .withJoin()
                .source(StateMachineStates.WORK_PERMIT_CHECK_FINISHED)
                .source(StateMachineStates.SECURITY_CHECK_FINISHED)
                .target(StateMachineStates.ALL_CHECK_FINISHED);
    }
}
