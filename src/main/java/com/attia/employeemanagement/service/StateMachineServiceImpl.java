package com.attia.employeemanagement.service;

import com.attia.employeemanagement.model.Employee;
import com.attia.employeemanagement.repository.EmployeeRepository;
import com.attia.employeemanagement.statemachine.StateMachineEvents;
import com.attia.employeemanagement.statemachine.StateMachineStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    private static final Logger LOG = LoggerFactory.getLogger(StateMachineServiceImpl.class);
    private final org.springframework.statemachine.service.StateMachineService<StateMachineStates, StateMachineEvents> stateMachineService;
    private final EmployeeRepository employeeRepository;

    public StateMachineServiceImpl(org.springframework.statemachine.service.StateMachineService<StateMachineStates, StateMachineEvents> stateMachineService, EmployeeRepository employeeRepository) {
        this.stateMachineService = stateMachineService;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public boolean executeTransition(Employee employee, StateMachineEvents event) {
        final String methodName = "executeTransition()";
        StateMachine<StateMachineStates, StateMachineEvents> sm = stateMachineService.acquireStateMachine(employee.getId().toString());

        if (event == null) {
            LOG.error("{} Unkown event: {}.", methodName, event);
        }

        Message<StateMachineEvents> message = MessageBuilder.withPayload(event)
                .setHeader("employeeId", employee.getId())
                .build();

        boolean success = sm.sendEvent(message);
        List<StateMachineStates> stateMachineStates = new ArrayList<>(sm.getState().getIds());
        if (success) {
            updateEmployeeStatus(employee, stateMachineStates);
        }
        return success;
    }

    public void updateEmployeeStatus(Employee employee, List<StateMachineStates> stateMachineStates) {
        employee.setStatus(stateMachineStates);
        employeeRepository.save(employee);
    }
}
