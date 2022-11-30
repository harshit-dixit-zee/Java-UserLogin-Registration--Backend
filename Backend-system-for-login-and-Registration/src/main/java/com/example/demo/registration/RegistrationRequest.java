package com.example.demo.registration;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Component
public class RegistrationRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
