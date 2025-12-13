package com.example.devSync.dto;


import com.example.devSync.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    

  
}
