package com.minesweeper.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    // Getters and Setters
    private Long id;
    private String email;
    private String name;

    // Constructors
    public UserDTO() { }

    public UserDTO(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}