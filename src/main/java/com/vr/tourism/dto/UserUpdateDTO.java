package com.vr.tourism.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;

    private String email;
//    private String password;
    private String username;
    private String fullName;
    private String avatar;

}
