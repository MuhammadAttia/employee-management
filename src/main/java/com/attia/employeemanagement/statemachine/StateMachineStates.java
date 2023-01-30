package com.attia.employeemanagement.statemachine;

public enum StateMachineStates {
    ADDED,
    IN_CHECK,
    SECURITY_CHECK_STARTED,
    SECURITY_CHECK_FINISHED,
    WORK_PERMIT_CHECK_STARTED,
    WORK_PERMIT_CHECK_PENDING_VERIFICATION,
    WORK_PERMIT_CHECK_FINISHED,
    ALL_CHECK_FINISHED,
    APPROVED,
    ACTIVE;

    public static StateMachineStates get(final String state) {
        for (StateMachineStates st : StateMachineStates.values()) {
            if (st.name().equals(state))
                return st;
        }
        return null;
    }
}
