package com.example.demo.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    RegistrationRequest authenticationRequest;

    @Autowired
    RegistrationService registrationService;

    @PostMapping
    public String registration(@RequestBody RegistrationRequest request)
    {
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam(name = "token") String token)
    {
        return registrationService.confirmToken(token);
    }
}
