package com.globex.userdto;



import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TodoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private long id;

    private String todoId;
    private String name;
    private String description;
    private double price;
    private Date manufactureTime;
    private Date expTime;
}
