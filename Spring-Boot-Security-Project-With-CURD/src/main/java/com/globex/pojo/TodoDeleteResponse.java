package com.globex.pojo;

import lombok.Data;

@Data
public class TodoDeleteResponse {
    private String todoId;
    private String name;
    private String description;

}
