package com.globex.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class TodoResponse {
    private String todoId;
    private String name;
    private String description;
    private double price;
    private Date manufactureTime;
    private Date expTime;
}
