package com.hasan.client;

import org.springframework.stereotype.Component;

import com.hasan.DTO.CustomerResponse;
import com.hasan.exception.ServiceUnavailableException;

@Component
public class ClientFallback implements CustomerClient {

	@Override
	public CustomerResponse getCustomer(Long id) {
		throw new ServiceUnavailableException("Customer service is temporary unavailable, Please try again later.");
	}

}