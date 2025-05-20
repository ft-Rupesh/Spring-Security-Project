package com.globex.model;

import lombok.Data;

@Data
public class CreateUserResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;


}
