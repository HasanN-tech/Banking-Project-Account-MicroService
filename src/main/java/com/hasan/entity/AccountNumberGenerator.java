package com.hasan.entity;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

	public String generate() {

		long value = System.currentTimeMillis();
		Random num=new Random();
		num.longs(1000000000);

		return "RJF" + value;
	}
}