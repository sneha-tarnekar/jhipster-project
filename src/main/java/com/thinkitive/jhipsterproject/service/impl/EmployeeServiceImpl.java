package com.thinkitive.jhipsterproject.service.impl;

import com.thinkitive.jhipsterproject.domain.Employee;
import com.thinkitive.jhipsterproject.repository.EmployeeRepository;
import com.thinkitive.jhipsterproject.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Mono<Employee> update(Employee employee) {
        log.debug("Request to update Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Mono<Employee> partialUpdate(Employee employee) {
        log.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getName() != null) {
                    existingEmployee.setName(employee.getName());
                }
                if (employee.getEmployeeId() != null) {
                    existingEmployee.setEmployeeId(employee.getEmployeeId());
                }
                if (employee.getAge() != null) {
                    existingEmployee.setAge(employee.getAge());
                }
                if (employee.getGender() != null) {
                    existingEmployee.setGender(employee.getGender());
                }

                return existingEmployee;
            })
            .flatMap(employeeRepository::save);
    }

    @Override
    public Flux<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAllBy(pageable);
    }

    public Mono<Long> countAll() {
        return employeeRepository.count();
    }

    @Override
    public Mono<Employee> findOne(String id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Employee : {}", id);
        return employeeRepository.deleteById(id);
    }
}
