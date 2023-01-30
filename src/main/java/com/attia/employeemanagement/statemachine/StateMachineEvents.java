package com.attia.employeemanagement.statemachine;

public enum StateMachineEvents {
    BEGIN_CHECK,
    COMPLETE_INITIAL_WORK_PERMIT_CHECK,
    FINISH_WORK_PERMIT_CHECK,
    FINISH_SECURITY_CHECK,
    ACTIVATE;
    
    public static StateMachineEvents get(final String event) {
        for(StateMachineEvents ev : StateMachineEvents.values()) {
            if(ev.name().equals(event))
                return ev;
        }
        return null;
    }
}
