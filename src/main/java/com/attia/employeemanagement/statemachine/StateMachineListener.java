package com.attia.employeemanagement.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class StateMachineListener extends StateMachineListenerAdapter<StateMachineStates, StateMachineEvents>  {
    private static final Logger LOG = LoggerFactory.getLogger(StateMachineListener.class);

    @Override
    public void stateChanged(State<StateMachineStates, StateMachineEvents> from, State<StateMachineStates, StateMachineEvents> to) {
        super.stateChanged(from, to);
        LOG.info("State changed to: {}", to.getId().name());
}

    @Override
    public void eventNotAccepted(Message<StateMachineEvents> event) {
        super.eventNotAccepted(event);
        LOG.error("State change failed: {}", event.getPayload());
    }
}