package com.webage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webage.security.TokenService;

@RestController
public class AuthController {

    //  PART 3a -
    //  Add code here to define a member variable of type TokenService.
    //  Annotate this with @Autowired so Spring can dependency inject it:



    //  PART 3b -
    //  Add code here to define a method to respond to POST requests on the /token endpoint.
    //  The method should take one parameter of type Authentication and return a String.
    //  The method should return the result of a call the tokenService’s generateToken() method:




    @GetMapping("/")
    public String health() {
        return "Authorization Server is running.";
    }

}