package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.domain.exception.EmployerNotFoundException;
import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import edu.sjsu.cmpe275.web.exception.OperationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class EmployerService {
    private EmployerRepository employerRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployerService(
            final EmployerRepository employerRepository,
            final EmployeeRepository employeeRepository
    ) {
        this.employerRepository = employerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employer createEmployer(final Employer employer) {
        return employerRepository.save(employer);
    }

    public Employer findEmployer(final Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException(id));
    }

    @Transactional
    public Employer updateEmployer(final Long id, final Employer fromRequest) {
        final Employer existingEmployer = findEmployer(id);
        existingEmployer.update(fromRequest);
        return existingEmployer;
    }

    @Transactional
    public Employer deleteEmployer(final Long id) {
        final Employer toDelete = findEmployer(id);
        final List<Employee> allEmployee = employeeRepository.findAll();
        for (Employee emp: allEmployee) {
            if (emp.getEmployer().getId() == id) {
                throw new OperationNotAllowedException(
                        "There are still employee belonging to this employer",
                        id.toString()
                );
            }
        }
        employerRepository.delete(toDelete);
        return toDelete;
    }
}
