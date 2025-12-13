package com.example.devSync.mapper;

import com.example.devSync.dto.UserDTO;
import com.example.devSync.entity.User;

public class UserMapper {

    //Entity to DTO
    public static UserDTO toUserDTO (User user){
        if(user==null){
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return userDTO;


    }

    //DTO to Entity
    public static User toUserEntity(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }
}
