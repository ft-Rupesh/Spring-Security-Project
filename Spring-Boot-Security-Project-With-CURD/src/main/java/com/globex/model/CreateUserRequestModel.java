package com.globex.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateUserRequestModel {


    @NotNull(message = "FirstName cannot be Null")
    private String firstName;
    @NotNull(message = "LastName cannot be Null")
    private String lastName;
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;
    @NotNull(message = "password cannot be null")
    @Size(min = 8,max = 16)
    private String password;
}
