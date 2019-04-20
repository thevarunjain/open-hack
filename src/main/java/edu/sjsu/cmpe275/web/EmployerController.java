package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.service.EmployerService;
import edu.sjsu.cmpe275.web.mapper.EmployerMapper;
import edu.sjsu.cmpe275.web.model.response.EmployerDto;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private final EmployerService employerService;
    private final EmployerMapper employerMapper;

    @Autowired
    public EmployerController(
            EmployerService employerService,
            EmployerMapper employerMapper
    ) {
        this.employerService = employerService;
        this.employerMapper = employerMapper;
    }

    /***
     *  Creates Employer
     * @param params Request query parameters
     *               name mandatory
     * @return 200 - Successful creation of employer and returns employer object
     *         400 - Invalid request
     */
    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public EmployerDto createEmployer(@RequestParam Map<String, String> params) {

        ValidatorUtil.validateParams(params, Arrays.asList("name"));

        final Employer employer = employerService.createEmployer(employerMapper.map(params));

        return employerMapper.map(employer);
    }

    /***
     * Get Employer
     * @param id Employer ID
     * @return 200 - Successful get of employer and returns employer object
     *         400 - Invalid request
     *         404 - If employee does not exist
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployerDto getEmployer(@PathVariable @NotNull Long id) {
        return employerMapper.map(employerService.findEmployer(id));
    }

    /***
     * Updates Employer
     * @param id Employer ID
     * @param params Request query parameters
     *               name is mandatory
     * @return 200 - Successful update of employer and returns updated employer object
     *         400 - Invalid request
     *         404 - If employer does not exist
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployerDto updateEmployer(@PathVariable @NotNull Long id, @RequestParam Map<String, String> params) {

        ValidatorUtil.validateParams(params, Arrays.asList("name"));

        final Employer updatedEmployer = employerService.updateEmployer(
                id,
                employerMapper.map(params)
        );

        return employerMapper.map(updatedEmployer);

    }

    /***
     * Deletes Employer
     * @param id Employer ID
     * @return 200 - Successful deletion of employer and returns employer object before deletion
     *         400 - Invalid request if employer still has employees
     *         404 - If employer does not exist
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployerDto deleteEmployer(@PathVariable @NotNull long id) {
        final Employer deletedEmployer = employerService.deleteEmployer(id);
        return employerMapper.map(deletedEmployer);
    }
}
