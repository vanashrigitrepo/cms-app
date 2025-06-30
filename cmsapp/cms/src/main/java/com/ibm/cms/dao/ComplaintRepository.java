package com.ibm.cms.dao;

import com.ibm.cms.dto.CompliantResponseStatusCountDto;
import com.ibm.cms.entity.ComplaintEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<ComplaintEntity, Long> {

    // ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅  Custom Methods According Requirements ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅

    //✅Search complaints by title or category ✅ Ignore deleted complaints ✅ Support case-insensitive keyword search
    @Query("SELECT c FROM ComplaintEntity c WHERE " + "(LOWER(c.title) LIKE (CONCAT('%', :keyword, '%')) " +
    "OR LOWER(c.category) LIKE (CONCAT('%', :keyword, '%'))) " + "AND c.deleted=false")
    public List<ComplaintEntity> searchByKeywordEitherInTitleOrCategory (@Param("keyword") String keyword);

    //✅Search by Status
    @Query("SELECT c FROM ComplaintEntity c WHERE "+ "LOWER(c.status) = LOWER(:status) "+ "AND c.deleted=false")
public List<ComplaintEntity> findByStatus(String status);
    // OR public List<ComplaintEntity> findByStatusIgnoreCaseAndDeletedFalse(String status) spring automatically write query for it

//✅Search by category
    public List<ComplaintEntity> findByCategoryIgnoreCaseAndDeletedFalse(String category);

//✅Count Number of Complaints
    public long countByDeletedFalse();

//✅Count Number of Complaints based on their status
    @Query("SELECT c.status, COUNT(c) FROM ComplaintEntity c GROUP BY c.status")
    public List<Object[]> countByStatus();

//✅ Step-by-Step Pagination + Sorting Setup
  public Page<ComplaintEntity> findByDeletedFalse(Pageable pageable);

  //✅ Count NUmber of compliants by according status By Creating custom Dto
    @Query("SELECT COUNT(c.status) FROM ComplaintEntity c WHERE "+" LOWER(c.status) = (:status) "+ "AND c.deleted=false")
public long countByGivenStatus (@Param("status") String status);

//✅ Search By Category/ Status/ Keyword and also adding pagination to it
@Query("SELECT c FROM ComplaintEntity c WHERE " +
        "(:status IS NULL OR LOWER(c.status) LIKE LOWER(CONCAT('%', :status, '%'))) AND " +
        "(:category IS NULL OR LOWER(c.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
        "(:keyword IS NULL OR (" +
        "LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
        "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
        ")) AND " +
        "c.deleted = false")

    public Page<ComplaintEntity> paginationAndFilterByStatusOrCategoryOrKeywordAndDeletedFalse (Pageable pageable,
                                                                                                @Param("status") String status,
                                                                                                @Param("category") String category,
                                                                                                @Param("keyword") String keyword);




}
