package com.lab.services.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class User {
    @NotNull
    @NotEmpty
    private String name;
    private String id;
}
