package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.service.EmployeeService;
import edu.sjsu.cmpe275.web.model.response.SuccessResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    private final EmployeeService employeeService;


    @Autowired
    public CollaboratorController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /***
     * Creates collaboration between the two employees with the given IDs
     * @param id1 Employee ID for creating collaboration
     * @param id2 Employee ID for creating collaboration
     * @return 200 Success message after successful collaboration creation
     *         404 If employee does not exist
     */
    @PutMapping(value = "/{id1}/{id2}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseDto createCollaboration(@PathVariable @NotNull Long id1,
                                                  @PathVariable @NotNull Long id2
    ) {
        employeeService.createCollaboration(
                id1, id2
        );
        return new SuccessResponseDto("Collaboration created successfully");
    }

    /***
     * Removes collaboration between the two employees with the given IDs
     * @param id1 Employee ID for removing collaboration
     * @param id2 Employee ID for removing collaboration
     * @return 200 Success message after successful collaboration removal
     *         404 If employee does not exist
     */
    @DeleteMapping(value = "/{id1}/{id2}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseDto deleteEmployee(@PathVariable @NotNull long id1,
                               @PathVariable @NotNull long id2
    ) {
        employeeService.deleteCollaboration(
                id1, id2
        );
        return new SuccessResponseDto("Collaboration deleted successfully");
    }

}
