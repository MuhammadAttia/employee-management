package com.attia.employeemanagement.controller;


import com.attia.employeemanagement.service.EmployeeService;
import org.openapitools.api.EmployeesApi;
import org.openapitools.model.EmployeeRequest;
import org.openapitools.model.EmployeeResponse;
import org.openapitools.model.UpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmployeeController implements EmployeesApi {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<EmployeeResponse> addEmployee(EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.addEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployee(Long employeeId) {
        EmployeeResponse employee = employeeService.getEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(employee);

    }


    @Override
    public ResponseEntity<EmployeeResponse> updateEmployeeStatus(Long id, UpdateRequest updateRequest) {
        EmployeeResponse response = employeeService.updateEmployeeStatus(id, updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
