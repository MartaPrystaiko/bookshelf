package com.learnjava.bookshelf.controller;

import com.learnjava.bookshelf.dto.AuthRequest;
import com.learnjava.bookshelf.dto.AuthResponse;
import com.learnjava.bookshelf.dto.UserDto;
import com.learnjava.bookshelf.service.JwtServiceImpl;
import com.learnjava.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
//@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtServiceImpl jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "/user")
    public String register(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping(value = "/token")
    public AuthResponse generateToken(@RequestBody AuthRequest authRequest) {
        final Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (!authenticate.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user with such username: " + authRequest.getUsername());
        }
        final String token = jwtService.generateToken(authRequest.getUsername(), "My Token");
        return new AuthResponse(token);
    }

}
