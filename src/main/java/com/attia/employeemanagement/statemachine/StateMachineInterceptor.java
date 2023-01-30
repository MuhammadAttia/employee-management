package com.attia.employeemanagement.statemachine;

import com.attia.employeemanagement.model.Employee;
import com.attia.employeemanagement.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Intercept and manage update operations
 * 
 */
@Component
public class StateMachineInterceptor extends StateMachineInterceptorAdapter<StateMachineStates, StateMachineEvents> {
    private static final Logger LOG = LoggerFactory.getLogger(StateMachineInterceptor.class);

    private final EmployeeRepository employeeRepository;

    public StateMachineInterceptor(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void preStateChange(State<StateMachineStates, StateMachineEvents> state, Message<StateMachineEvents> message, Transition<StateMachineStates, StateMachineEvents> transition, StateMachine<StateMachineStates, StateMachineEvents> stateMachine, StateMachine<StateMachineStates, StateMachineEvents> rootStateMachine) {
        Employee employee = employeeRepository.findById((long) Integer.parseInt(rootStateMachine.getId())).orElse(null);

        if (employee == null) {
            LOG.error("Employee not found, unable to update status.");
            return;
        }
        employee.setStatus(new ArrayList<>(stateMachine.getState().getIds()));
        employeeRepository.save(employee);
    }

    @Override
    public Exception stateMachineError(StateMachine<StateMachineStates, StateMachineEvents> stateMachine, Exception exception) {
        LOG.error("{} StateMachine encountered error: [ message: {}]", "stateMachineError()", exception.getMessage());
        return super.stateMachineError(stateMachine, exception);
    }
}
