package com.ibm.cms.mapper;

import com.ibm.cms.dto.ComplaintRequestDto;
import com.ibm.cms.dto.ComplaintResponseDto;
import com.ibm.cms.entity.ComplaintEntity;

public class ComplaintMapper {

    public static ComplaintEntity mapToEntity(ComplaintRequestDto complaintRequestDto){
        ComplaintEntity complaintEntity=ComplaintEntity.builder()
                .title(complaintRequestDto.getTitle())
        .description(complaintRequestDto.getDescription())
                .category(complaintRequestDto.getCategory())
                .status(complaintRequestDto.getStatus())
                .submittedBy(complaintRequestDto.getSubmittedBy())
                .createdAt(complaintRequestDto.getCreatedAt())
                .updatedAt(complaintRequestDto.getUpdatedAt())
                .build();
        return complaintEntity;
    }

    // to get all data

    public static ComplaintResponseDto mapToBody(ComplaintEntity complaintEntity){
        ComplaintResponseDto complaintResponseDto= ComplaintResponseDto.builder()
                .id(complaintEntity.getId())
                .title(complaintEntity.getTitle())
                        .description(complaintEntity.getDescription())
                        .category(complaintEntity.getCategory())
                        .status(complaintEntity.getStatus())
                        .submittedBy(complaintEntity.getSubmittedBy())
                        .createdAt(complaintEntity.getCreatedAt())
                        .updatedAt(complaintEntity.getUpdatedAt())
                        .build();

return complaintResponseDto;
    }



}
