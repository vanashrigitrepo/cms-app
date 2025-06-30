package com.ibm.cms.serviceImpl;

import com.ibm.cms.dao.ComplaintRepository;
import com.ibm.cms.dto.ComplaintRequestDto;
import com.ibm.cms.dto.ComplaintResponseDto;
import com.ibm.cms.dto.CompliantResponseStatusCountDto;
import com.ibm.cms.entity.ComplaintEntity;
import com.ibm.cms.exception.IdNotFound;
import com.ibm.cms.exception.KeywordNotFound;
import com.ibm.cms.exception.StatusNotFoundException;
import com.ibm.cms.mapper.ComplaintMapper;
import com.ibm.cms.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompaintServiceImpl implements ComplaintService {


    @Autowired
    public ComplaintRepository complaintRepository;
    // logic to save complaint in db
    /**
     * @param complaintdata
     */
    @Override
    public void complaintAdded(ComplaintRequestDto complaintRequestDto) {
        log.info("Saving complaint's of type : {} ", complaintRequestDto.getCategory());
        //logic
ComplaintEntity complaint= ComplaintMapper.mapToEntity(complaintRequestDto);
        complaintRepository.save(complaint);
    }

    // ✅ Get all non-deleted complaint data and return as DTOs

    /**
     * @return
     */
    @Override
    public List<ComplaintResponseDto> getAllData() {


        List<ComplaintEntity> complaintEntity= complaintRepository.findAll();
      return  complaintEntity.stream()
                .map(ComplaintMapper::mapToBody)
                .collect(Collectors.toList());

    }

    // ✅ Get data by id and return as DTOs

    /**
     * @param complaintId
     * @return
     */
    @Override
    public ComplaintResponseDto getById(Long complaintId) {
log.info("Fetch data by ID: {} ", complaintId);
//logic

ComplaintEntity complaint = complaintRepository.findById(complaintId)
        .orElseThrow(() -> new IdNotFound(complaintId));

        return ComplaintMapper.mapToBody(complaint);
    }

    /**
     * @param complaintId
     * @param complaintResponseDto
     * @return
     */
    @Override
    public ComplaintResponseDto updateData(Long complaintId, ComplaintRequestDto complaintRequestDto) {
log.info("Updating data by its id:{} ", complaintId);

    //logic

        ComplaintEntity complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new IdNotFound(complaintId));

complaint.setTitle(complaintRequestDto.getTitle());
complaint.setDescription(complaintRequestDto.getDescription());
complaint.setCategory(complaintRequestDto.getCategory());
complaint.setUpdatedAt(complaintRequestDto.getUpdatedAt());
complaint.setStatus(complaintRequestDto.getStatus());
complaint.setCreatedAt(complaintRequestDto.getCreatedAt());
complaint.setSubmittedBy(complaintRequestDto.getSubmittedBy());
ComplaintEntity complaint1 = complaintRepository.save(complaint);

log.info("Updated data in id {} ", complaintId);

return ComplaintMapper.mapToBody(complaint1);

    }

    /**
     * @param complaintId
     */
    @Override
    public void deleteData(Long complaintId) {
        log.info("Data to delete using id {}", complaintId);
        ComplaintEntity complaint= complaintRepository.findById(complaintId).orElseThrow(() -> new IdNotFound(complaintId));
complaint.setDeleted(true);
complaintRepository.save(complaint);
    }

    /**
     * @param id
     */
    @Override
    public void restore(Long id) {
log.info("Restoring deleted data of id {}", id);

ComplaintEntity complaint= complaintRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
complaint.setDeleted(false);
complaintRepository.save(complaint);
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<ComplaintResponseDto> searchByWord(String keyword) {
        log.info("Getting records by keyword {} ", keyword);
        //
        if(keyword.trim().isEmpty() || keyword == null || !keyword.matches(".*[azAZ0-9].*")){
            throw new KeywordNotFound(keyword);
        }
        if(keyword.isEmpty()){
            throw new KeywordNotFound(keyword);
        }
        List<ComplaintEntity> complaint = complaintRepository.searchByKeywordEitherInTitleOrCategory(keyword);
        return complaint.stream()
                .map(ComplaintMapper::mapToBody)
                .collect(Collectors.toList());

    }

    /**
     * @param status
     * @return
     */
    @Override
    public List<ComplaintResponseDto> fetchByStatus(String status) {
        log.info("get by complaint status {}", status);

        if(status == null || status.trim().isEmpty() || status.matches(".*[azAZ].*")){
            throw new StatusNotFoundException(status);
        }
       List<ComplaintEntity> complaint = complaintRepository.findByStatus(status);

       return complaint.stream()
               .map(ComplaintMapper::mapToBody)
               .collect(Collectors.toList());




    }

    /**
     * @param Category
     * @return
     */
    @Override
    public List<ComplaintResponseDto> fetchByCategory(String category) {
List<ComplaintEntity> complaint = complaintRepository.findByCategoryIgnoreCaseAndDeletedFalse(category);
return complaint.stream()
        .map(ComplaintMapper::mapToBody)
        .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public long countOfRecords() {
log.info("Get Total Complaints Count");
long complaint = complaintRepository.countByDeletedFalse();
return complaint;
    }

    /**
     * @return
     */
    @Override
    public Map<String, Long> countBySatatus() {
        log.info("fetch count according to status");
        List<Object[]> complaints = complaintRepository.countByStatus();
        Map<String, Long> result=new HashMap<>();
    for(Object[] rows:complaints){
        String status=(String) rows[0];
        Long count=(Long) rows[1];
        if (status != null) {
            result.put(status, count);
        }
        }
    return result;
    }

    /**
     * @param page
     * @param size
     * @param sortBy
     * @return
     */
    @Override
    public Page<ComplaintResponseDto> paginationAndSortById(int page, int size, String sortBy) {
log.info("Adding Pagination {} {} {} ", page, size, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<ComplaintEntity> complaintEntityPage = complaintRepository.findByDeletedFalse(pageable);
        return complaintEntityPage.map(ComplaintMapper::mapToBody);
    }

    /**
     * @param status
     * @return
     */
    @Override
    public long fetchGivenByStatus(String status) {
log.info("Fetch data {}", status);
long complaintEntities = complaintRepository.countByGivenStatus(status);
return complaintEntities;

    }

    /**
     * @param pageable
     * @param status
     * @param category
     * @param keyword
     * @return
     */
    @Override
    public Page<ComplaintResponseDto> paginationAndFilterComplaints(int start, int size,String sortBy, String status, String category, String keyword) {
        log.info("Adding Pagination and filter by status {} category {} and keyword {} ", status, category, keyword);
        Pageable pageable = PageRequest.of(start, size, Sort.by(sortBy).ascending());
        Page<ComplaintEntity> complaintResponseDtoPage = complaintRepository.paginationAndFilterByStatusOrCategoryOrKeywordAndDeletedFalse(pageable, status, category, keyword);
return complaintResponseDtoPage.map(ComplaintMapper::mapToBody);

    }


}
