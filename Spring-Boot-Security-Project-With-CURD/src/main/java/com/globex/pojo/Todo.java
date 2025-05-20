package com.globex.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 100)
    private String todoId;

    @Column(nullable = false,length = 100)
    private String name;
    @Column(nullable = false,length = 100)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureTime;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date expTime;

}
