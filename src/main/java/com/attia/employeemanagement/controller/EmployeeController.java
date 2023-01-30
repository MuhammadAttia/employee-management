package com.attia.employeemanagement.controller;



import com.attia.employeemanagement.repository.EmployeeRepository;
import com.attia.employeemanagement.service.EmployeeService;
import org.modelmapper.ModelMapper;
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

    private final EmployeeRepository employeeRepository;

    private final ModelMapper mapper;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<EmployeeResponse> addEmployee(EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.addEmployee(employeeRequest);
        return   ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployee(Long employeeId) {
        EmployeeResponse employee = employeeService.getEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(employee);

    }


@Override
public ResponseEntity<EmployeeResponse> updateEmployeeStatus(Long id, UpdateRequest updateRequest) {
    EmployeeResponse response = employeeService.updateEmployeeStatus(id,updateRequest);
    return ResponseEntity.status(HttpStatus.OK).body(response);
}
}
