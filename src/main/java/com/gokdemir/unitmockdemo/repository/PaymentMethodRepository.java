package com.gokdemir.unitmockdemo.repository;

import com.gokdemir.unitmockdemo.model.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{

    @Query(value = "from PaymentMethod")
    Page<PaymentMethod> findAllPageable(Pageable pageable);
}
