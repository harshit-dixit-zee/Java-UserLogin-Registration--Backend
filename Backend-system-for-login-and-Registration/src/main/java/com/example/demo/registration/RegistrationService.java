package com.example.demo.registration;

import com.example.demo.AppUser.AppUser;
import com.example.demo.AppUser.AppUserRole;
import com.example.demo.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    RegistrationRequest authenticationRequest;
    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AppUserService appUserService;

    public String register(RegistrationRequest request) {

        Boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException("Email not valid");
        }
        return appUserService.SignUp(new AppUser(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER)
        );

        // Here, we will write business logic for autheticating the
        // check whether email is valid or not

    }
    public String confirmToken(String token)
    {
        return "confirmed";
    }

}
