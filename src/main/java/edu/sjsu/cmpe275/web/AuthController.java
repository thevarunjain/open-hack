package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.security.JwtTokenProvider;
import edu.sjsu.cmpe275.web.model.request.LoginRequestDto;
import edu.sjsu.cmpe275.web.model.response.JwtAuthenticationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(
        AuthenticationManager authenticationManager,
        JwtTokenProvider tokenProvider
    ){
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(value = "/signin")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponseDto authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return JwtAuthenticationResponseDto.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .build();
    }

}
