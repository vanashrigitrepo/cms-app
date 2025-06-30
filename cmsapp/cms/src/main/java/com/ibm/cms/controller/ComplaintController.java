package com.ibm.cms.controller;

import com.ibm.cms.dto.ComplaintRequestDto;
import com.ibm.cms.dto.ComplaintResponseDto;
import com.ibm.cms.dto.CompliantResponseStatusCountDto;
import com.ibm.cms.service.ComplaintService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Data
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    /**
     * adding complaint to db
     *
     * @param complaintEntity
     * @return
     */
    @PostMapping("/add-complaint")
    public ResponseEntity<String> addComplaint(@RequestBody ComplaintRequestDto complaintRequestDto) {
        complaintService.complaintAdded(complaintRequestDto);
        return new ResponseEntity<>("Complaint Added Successfully", HttpStatus.OK);
    }

    // ===================== To Fetch All Records =====================

    @GetMapping("/get-all-records")
    public ResponseEntity<List<ComplaintResponseDto>> FetchAllRecords() {

        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getAllData();
        return new ResponseEntity<>(complaintResponseDtoList, HttpStatus.OK);
    }

    // ================== To fetch data Records by ID =================

    @GetMapping("/get-data-bt/id/{complaintId}")

    public ResponseEntity<ComplaintResponseDto> getById(@PathVariable Long complaintId) {
        ComplaintResponseDto complaintResponseDto = complaintService.getById(complaintId);
        return new ResponseEntity<>(complaintResponseDto, HttpStatus.OK);
    }

// API to Update Record BY Its id

    @PutMapping("/update-data/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> updateDataById(@PathVariable Long complaintId, @RequestBody ComplaintRequestDto complaintRequestDto) {
        ComplaintResponseDto complaint = complaintService.updateData(complaintId, complaintRequestDto);

        return new ResponseEntity<>(complaint, HttpStatus.OK);
    }

    // Api to delete data

    @DeleteMapping("/delete-data-by-id/{complaintId}")
    public ResponseEntity<Void> dataToDelete(@PathVariable Long complaintId) {
        complaintService.deleteData(complaintId);
        return ResponseEntity.noContent().build();
    }


    // To Restore Deleted Data

    @PutMapping("/restore/deleted/data/{id}")
    public ResponseEntity<String> restoreData(@PathVariable Long id) {
        complaintService.restore(id);
        return new ResponseEntity<>("Data Restored Successfully", HttpStatus.OK);
    }

    // API to search by Keyword

    @GetMapping("/search/by/{keyword}")
    public ResponseEntity<List<ComplaintResponseDto>> searchByKeyword(@PathVariable String keyword) {
        List<ComplaintResponseDto> complaintResponseDto = complaintService.searchByWord(keyword);
        return new ResponseEntity<>(complaintResponseDto, HttpStatus.OK);
    }

    // API to search by Complaint Status

    @GetMapping("/serach/by/{status}")
    public ResponseEntity<List<ComplaintResponseDto>> getDataByStatus(@PathVariable String status) {
        log.info("Getting all records by its status");
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.fetchByStatus(status);
        return new ResponseEntity<>(complaintResponseDtoList, HttpStatus.OK);
    }

    // API to search by Compliant Category

    @GetMapping("/search/by/category/{category}")
    public ResponseEntity<List<ComplaintResponseDto>> getDataByCategory(@PathVariable String category) {
        log.info("Getting Records By Complaint Category {} ", category);
        List<ComplaintResponseDto> complaintRequestDto = complaintService.fetchByCategory(category);
        return new ResponseEntity<>(complaintRequestDto, HttpStatus.OK);
    }

    // API to Count Total NUmber of Complaints

    @GetMapping("/total/count/of/complaints")
    // public ResponseEntity<CountResponseDto> countAllComplaints(){
    public ResponseEntity<Long> countAllComplaints() {
        log.info("api for counting complaints");
        // OR To Show Msg + Count
        // long sample = complaintService.countOfRecords();
//CountResponseDto complaint = new CountResponseDto("Total NUmber of Complaints are: ", sample);
// output {"msg":"Total NUmber of Complaints are: ","count":21}
        // his shows directly number ---->

        long complaint = complaintService.countOfRecords();
        return new ResponseEntity<>(complaint, HttpStatus.OK);
    }

    // API to Count Total NUmber of Complaints by status

    @GetMapping("/count/by/status")
    public ResponseEntity<Map<String, Long>> getCountByStatus() {
        log.info("Get records by status");
        Map<String, Long> counts = complaintService.countBySatatus();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }

//✅ Step-by-Step Pagination + Sorting Setup

    @GetMapping("/pagination")
    public ResponseEntity<Page<ComplaintResponseDto>> addingPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String id){

Page<ComplaintResponseDto> complaintResponseDtoPage = complaintService.paginationAndSortById(page, size, id);
return new ResponseEntity<>(complaintResponseDtoPage, HttpStatus.OK);
}

//✅ Get Count BY sending status
@CrossOrigin(origins = "http://127.0.0.1:5500")

@GetMapping("/count/by/sending/status/{status}")
    public CompliantResponseStatusCountDto countByGivenStatus(@PathVariable String status){
        log.info("Count Number of Compliants by {}", status);
      long compliantResponseStatusCountDtos = complaintService.fetchGivenByStatus(status);
      CompliantResponseStatusCountDto complaintcount= new CompliantResponseStatusCountDto(status,compliantResponseStatusCountDtos);
return  complaintcount;
    }

// ======== PAginatin + Filter by status/category/keyword and deleted is false

    @GetMapping("/pagination/filter")
    public ResponseEntity<Page<ComplaintResponseDto>> filterComplaints(
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "") String status,
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {
        log.info("Filtering complaints with status={}, category={}, keyword={}", status, category, keyword);
        Page<ComplaintResponseDto> page = complaintService.paginationAndFilterComplaints(start, size, sortBy, status, category, keyword);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }








    //=======end f cls============

}


