package com.ibm.cms.dto;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    private String resetToken;
    private String newPassword;


    }

