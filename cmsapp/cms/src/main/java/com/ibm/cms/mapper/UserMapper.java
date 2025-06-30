package com.ibm.cms.mapper;

import com.ibm.cms.dto.UserRequestDto;
import com.ibm.cms.entity.Users;

public class UserMapper {

    public static Users mapJsonToEntity(UserRequestDto userRequestDto){

        Users users = Users.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .role("User")
                .build();

        return users;

    }

}
