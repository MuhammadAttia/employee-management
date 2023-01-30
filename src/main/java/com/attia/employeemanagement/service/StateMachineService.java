package com.attia.employeemanagement.service;

import com.attia.employeemanagement.model.Employee;
import com.attia.employeemanagement.statemachine.StateMachineEvents;

public interface StateMachineService {
    public  boolean executeTransition(Employee info, StateMachineEvents event);
}
