package com.ags.learn.microservices.springbootmicroservices.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.ags.learn.microservices.springbootmicroservices.dto.ExchangeRateDTO;
import com.ags.learn.microservices.springbootmicroservices.service.ExchangeRateService;

@RestController
@RequestScope
public class ExchangeRateController {

	@Autowired
	ExchangeRateService exchangeRateService;

	@GetMapping("/api/{date}/{fromCurrency}/{toCurrency}")
	public ResponseEntity<ExchangeRateDTO> getExchangeRate(
			@PathVariable("date") String date,
			@PathVariable("fromCurrency") String baseCurrency,
			@PathVariable("toCurrency") String targetCurrency) throws Exception{
		ExchangeRateDTO ex = exchangeRateService.getExchangeRate(date, baseCurrency, targetCurrency);
		return new ResponseEntity<>(ex, HttpStatus.OK);
	}

	@GetMapping("/api/{fromDate}/{toDate}/{fromCurrency}/{toCurrency}")
	public ResponseEntity<Map<String,Double>> getExRateTrend(
			@PathVariable("fromCurrency") String baseCurrency,
			@PathVariable("toCurrency") String targetCurrency,
			@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate) throws Exception{
		return new ResponseEntity<>(exchangeRateService.getExRateTrend(baseCurrency, targetCurrency, fromDate, toDate), HttpStatus.OK);
	}
}
