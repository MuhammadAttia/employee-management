package com.attia.employeemanagement.service;

import com.attia.employeemanagement.exception.BadRequestException;
import com.attia.employeemanagement.exception.NotFoundException;
import com.attia.employeemanagement.model.Employee;
import com.attia.employeemanagement.repository.EmployeeRepository;
import com.attia.employeemanagement.statemachine.StateMachineEvents;
import com.attia.employeemanagement.statemachine.StateMachineStates;
import org.modelmapper.ModelMapper;
import org.openapitools.model.EmployeeRequest;
import org.openapitools.model.EmployeeResponse;
import org.openapitools.model.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final ModelMapper mapper;
    private final EmployeeRepository employeeRepository;

    private final StateMachineService stateMachineService;

    public EmployeeServiceImpl(ModelMapper mapper, EmployeeRepository employeeRepository, StateMachineService stateMachineService) {
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
        this.stateMachineService = stateMachineService;
    }

    @Override
    public EmployeeResponse addEmployee(final EmployeeRequest data) {
        LOG.debug("Creating new employee: {}", data);
            
            OffsetDateTime now = OffsetDateTime.now();
            Employee employee = mapper.map(data, Employee.class);
            employee.setCreatedAt(now);
            employee.setModifiedAt(now);
            employee.setStatus(List.of(StateMachineStates.ADDED));

        Employee response = employeeRepository.save(employee);
        return mapper.map(response,EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse updateEmployeeStatus(Long id, UpdateRequest updateRequest)  {
        final String methodName = "updateEmployeeStatus()";
        LOG.info("Updating empoyeeId:{} status to: {}", id, updateRequest.getEvent());
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                boolean accepted = stateMachineService.executeTransition(employee.get(), StateMachineEvents.get(updateRequest.getEvent().getValue()));
                if (!accepted) {
                    LOG.error("{} Status update failed with event: {}.", methodName,  updateRequest.getEvent());
                    throw new BadRequestException(String.format("Translation from '%s' with '%s' is not allowed", employee.get().getStatus(), updateRequest.getEvent()));
                }
            }
        return getEmployee(id);
    }

    
    @Override
    public EmployeeResponse getEmployee(final Long id) {
        final String methodName = "getEmployee()";
        LOG.info("{} Get employee empoyeeId: {}", methodName, id);
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                return  mapper.map(employee.get(), EmployeeResponse.class);
            }
            throw new NotFoundException("Employee Not Found ");
    }

}
