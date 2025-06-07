package com.gokdemir.unitmockdemo.repository;

import com.gokdemir.unitmockdemo.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

    @Query(value = "from Payment")
    Page<Payment> findAllPageable(Pageable pageable);
}
