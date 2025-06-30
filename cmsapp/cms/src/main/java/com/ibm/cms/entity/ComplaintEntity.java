package com.ibm.cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Table(name="complaints")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComplaintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    private String status; // Pending, In Progress, Resolved

    private String submittedBy;

    private boolean deleted = false;

//    @Column(name = "created_at")
    private Date createdAt;


    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date(System.currentTimeMillis());
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date(System.currentTimeMillis());
    }


}



