package com.learn.demo.app_a_service.repository;

import com.learn.demo.app_a_service.dto.BillingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductARepository extends JpaRepository<BillingRequest,Long> {


}
