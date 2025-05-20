package com.globex.pojo;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoRequestModel {
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "Description must be included")
    private String description;
    @NotNull(message = "price cannot be null")
    private double price;
    @NotNull(message = "Manufacture time cannot be null")
    private Date manufactureTime;
    @NotNull(message = "Expiration time cannot be null")
    private  Date expTime;
}
