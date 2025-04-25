package edu.miu.cs.cs489.lesson6.citylibraryapp.model.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    // Add role field if needed
    private String role;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;



    // Constructors, getters, setters
}
