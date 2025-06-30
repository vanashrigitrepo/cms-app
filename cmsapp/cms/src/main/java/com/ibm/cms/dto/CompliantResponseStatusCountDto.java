package com.ibm.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompliantResponseStatusCountDto {
    private String status;
    private long count;
}
