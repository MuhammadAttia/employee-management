package com.attia.employeemanagement.service;

import com.attia.employeemanagement.model.Employee;
import com.attia.employeemanagement.statemachine.StateMachineEvents;
import com.attia.employeemanagement.statemachine.StateMachineStates;

import com.attia.employeemanagement.statemachine.StateMachineInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    private static final Logger LOG = LoggerFactory.getLogger(StateMachineServiceImpl.class);
    private final org.springframework.statemachine.service.StateMachineService<StateMachineStates, StateMachineEvents> stateMachineService;
    private final StateMachineInterceptor stateMachineInterceptor;

    public StateMachineServiceImpl(org.springframework.statemachine.service.StateMachineService<StateMachineStates, StateMachineEvents> stateMachineService, StateMachineInterceptor stateMachineInterceptor) {
        this.stateMachineService = stateMachineService;
        this.stateMachineInterceptor = stateMachineInterceptor;
    }


    @Override
    public boolean executeTransition(Employee employee, StateMachineEvents event) {
        final String methodName = "executeTransition()";
        StateMachine<StateMachineStates, StateMachineEvents> sm = stateMachineService.acquireStateMachine(employee.getId().toString());

        if(event == null) {
            LOG.error("{} Unkown event: {}.", methodName, event);
        }
        
        Message<StateMachineEvents> message = MessageBuilder.withPayload(event)
                .setHeader("employeeId", employee.getId())
                .build();
        
        sm.getStateMachineAccessor().doWithAllRegions(sma -> sma.addStateMachineInterceptor(stateMachineInterceptor));

        return sm.sendEvent(message);
    }
}
