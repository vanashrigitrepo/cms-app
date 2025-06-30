package com.ibm.cms.service;

import com.ibm.cms.dto.ComplaintRequestDto;
import com.ibm.cms.dto.ComplaintResponseDto;
import com.ibm.cms.dto.CompliantResponseStatusCountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ComplaintService {

    public void complaintAdded(ComplaintRequestDto complaintRequestDto);

// to fetch records

    public List<ComplaintResponseDto> getAllData();

    // to fetch record by ID

    public ComplaintResponseDto getById(Long complaintId);

    // To update record by its ID

    public ComplaintResponseDto updateData(Long complaintId, ComplaintRequestDto complaintRequestDto);

    // To Delete by Id

    public void deleteData(Long complaintId);

    // To Restore Deleted Data
    public void restore(Long id);

    // To Search BY Keyword
    public List<ComplaintResponseDto> searchByWord(String keyword);

    //To Search By Complaint Status
    public List<ComplaintResponseDto> fetchByStatus(String status);

    // To Search By Category
    public List<ComplaintResponseDto> fetchByCategory(String category);

    // Count Total Number of Complaints
    public long countOfRecords();

    // Count Total Number of Complaints By Status
    public Map<String, Long> countBySatatus();

    // Adding pagination
    public Page<ComplaintResponseDto> paginationAndSortById(int page, int size, String sortBy);

    // count complaints By Given Status
    public long fetchGivenByStatus(String status);

// Pagination + filter by status/category/keyword
    public Page<ComplaintResponseDto> paginationAndFilterComplaints(int start, int size,String sortBy, String status, String category, String keyword);





}
