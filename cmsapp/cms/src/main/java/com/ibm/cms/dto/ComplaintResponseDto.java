package com.ibm.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@Builder

public class ComplaintResponseDto {

    private long id;
    private String title;

    private String description;

    private String category;

    private String status; // Pending, In Progress, Resolved

    private String submittedBy;

    private boolean deleted = false;

    private Date createdAt;

    private Date updatedAt;

}
