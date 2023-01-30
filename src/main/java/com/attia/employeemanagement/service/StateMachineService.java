package com.attia.employeemanagement.service;

import com.attia.employeemanagement.model.Employee;
import com.attia.employeemanagement.statemachine.StateMachineEvents;

public interface StateMachineService {
    boolean executeTransition(Employee info, StateMachineEvents event);
}
