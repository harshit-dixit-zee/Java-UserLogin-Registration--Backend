package com.example.demo.AppUser;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenRepository;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@ToString
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email % username not found";
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    // Optional is a class that can contain both null as well as any value.
    // Optional can also be used for the method
    // here, we are calling findEmailById which is returning an Optional , so we can apply
    // .orElsethrow(exception)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("USER_NOT_FOUND",email )));
    }

    // It will register the user in the database after the validation from EmailValidator from registration package.
    // 1. Now first check ki kahi ye phle se database me to ni hai.
    //2. Hm User ko signUp krva rhe hain, agar vo phle se database me hai, to exception throw kr denge,
    // else, usko database me insert kr denge.
    // 3. Logic for checking
    //4. Use loadByEmail and get email from appUser
    public String SignUp(AppUser appUser){
        boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userExist){
            log.info("app crashing here");
            throw new IllegalStateException("user already present");
        }
        log.info("app crashing here");
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);


            return token;
    }

}

// 1. phle user ka email check krlo
// 2. phir password , we can leave because we have given NoOpPassword, but if have given bCrypt, then we van decrypt the password, and then
//