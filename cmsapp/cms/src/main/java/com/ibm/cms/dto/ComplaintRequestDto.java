package com.ibm.cms.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ComplaintRequestDto {

    // mot needed bcz its auto increamented private Long id;

    private String title;

    private String description;

    private String category;

    private String status; // Pending, In Progress, Resolved

    private String submittedBy;

    private boolean deleted = false;

    private Date createdAt;

    private Date updatedAt;
}
