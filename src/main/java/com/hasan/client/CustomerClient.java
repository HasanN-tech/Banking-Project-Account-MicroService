package com.hasan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hasan.DTO.CustomerResponse;

@FeignClient(name = "customer-service", fallback = ClientFallback.class)
public interface CustomerClient {

	@GetMapping("/api/customers/{id}")
	CustomerResponse getCustomer(@PathVariable Long id);
}