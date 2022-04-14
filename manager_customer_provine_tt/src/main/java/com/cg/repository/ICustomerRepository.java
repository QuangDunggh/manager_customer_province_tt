package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long> {

    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable);

  //  @Query(value = "SELECT * FROM customers WHERE firstName LIKE :keySearch OR lastName LIKE :keySearch",nativeQuery = true);
    List<Customer> findByFistNameOrLastNames(@Param("keySearch") String keySearch);
}
