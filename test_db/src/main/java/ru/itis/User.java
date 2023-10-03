package ru.itis;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}

