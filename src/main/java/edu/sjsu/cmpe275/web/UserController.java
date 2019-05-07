package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.UserMapper;
import edu.sjsu.cmpe275.web.model.request.CreateUserRequestDto;
import edu.sjsu.cmpe275.web.model.request.UpdateUserRequestDto;
import edu.sjsu.cmpe275.web.model.response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getUsers(@RequestParam(required = false) String name) {
        List<User> allUsers = userService.findUsers(name);
        return userMapper.map(allUsers);
    }

    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(
            @Valid @RequestBody CreateUserRequestDto toCreate,
            Errors validationErrors
    ) {
        // TODO Custom error on validation failure
        if (validationErrors.hasErrors()) {

        }
        User createdUser  = userService.createUser(userMapper.map(toCreate));
        return userMapper.map(createdUser);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUser(@PathVariable @NotNull Long id) {
        User user  = userService.findUser(id);
        return userMapper.map(user);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUser(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody UpdateUserRequestDto fromRequest
    ) {
        User fromUpdate = userMapper.map(fromRequest);
        User updatedUser  = userService.updateUser(id, fromUpdate);
        return userMapper.map(updatedUser);
    }

}
