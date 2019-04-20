package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.service.EmployeeService;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import edu.sjsu.cmpe275.web.model.response.EmployeeDto;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(
            EmployeeService employeeService,
            EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    /***
     * Creates Employee
     * @param params Request query parameters
     *               name, email and employerId is mandatory
     * @return 200 - Successful creation of employee and returns employee object
     *         400 - Invalid request
     *         404 - If employer does not exist
     */

    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@RequestParam Map<String, String> params) {
        ValidatorUtil.validateParams(params, Arrays.asList("name", "email", "employerId"));
        ValidatorUtil.validateRestrictedParam(params, Arrays.asList("collaborators", "reports"));

        final Employee createdEmployee = employeeService.createEmployee(
                employeeMapper.map(params),
                params.get("managerId"),
                params.get("employerId")
        );

        return employeeMapper.map(createdEmployee);
    }

    /***
     * Get Employee
     * @param id Employee ID
     * @return 200 - Successful get of employee and returns employee object
     *         400 - Invalid request
     *         404 - If employee does not exist
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployee(@PathVariable @NotNull Long id) {
        return employeeMapper.map(employeeService.findEmployee(id));
    }

    /***
     * Update Employee
     * @param id Employee ID
     * @param params Request query parameters
     *               email and employerId is mandatory
     * @return 200 - Successful update of employee and returns updated employee object
     *         400 - Invalid request
     *         404 - If employee does not exist
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@PathVariable @NotNull Long id, @RequestParam Map<String, String> params) {
        ValidatorUtil.validateParams(params, Arrays.asList("email", "employerId"));
        ValidatorUtil.validateRestrictedParam(params, Arrays.asList("collaborators", "reports"));

        final Employee updatedEmployee = employeeService.updateEmployee(
                id,
                employeeMapper.map(params),
                params.get("managerId"),
                params.get("employerId")
        );

        return employeeMapper.map(updatedEmployee);

    }

    /***
     *
     * @param id Employee ID
     * @return 200 - Successful deletion of employee and returns employee object before deletion
     *         400 - Invalid request if employee still has reports
     *         404 - If employee does not exist
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto deleteEmployee(@PathVariable @NotNull long id) {
        final Employee deletedEmployee = employeeService.deleteEmployee(id);
        return employeeMapper.map(deletedEmployee);
    }
}