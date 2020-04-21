package com.ags.learn.microservices.springbootmicroservicesFE.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ags.learn.microservices.springbootmicroservicesFE.dto.ExchangeRateDTO;
import com.ags.learn.microservices.springbootmicroservicesFE.dto.ExchangeRateTrendDTO;
import com.ags.learn.microservices.springbootmicroservicesFE.service.ExchangeRateTrendService;


@RestController
public class FEController {

	@Autowired
	ExchangeRateTrendService exchangeRateTrendService;

	@GetMapping("/report/ex-rate/{fromDate}/{toDate}/{fromCurrency}/{toCurrency}")
	public ResponseEntity<ExchangeRateTrendDTO> getExchangeRateTrend(
			@PathVariable("fromCurrency") String fromCurrency,
			@PathVariable("toCurrency") String toCurrency,
			@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate
			)
					throws Exception{
		return new ResponseEntity<>(exchangeRateTrendService.
				retrieveExRateTrend(fromCurrency, toCurrency, fromDate, toDate), HttpStatus.OK);
	}
	
	@GetMapping("/api/{date}/{fromCurrency}/{toCurrency}")
	public ResponseEntity<ExchangeRateDTO> getExchangeRate(
			@PathVariable("date") String date,
			@PathVariable("fromCurrency") String baseCurrency,
			@PathVariable("toCurrency") String targetCurrency) throws Exception{
		//ExchangeRateDTO exchangeRateDTO = exchangeRateService.getExchangeRate(date, baseCurrency, targetCurrency);
		ExchangeRateDTO ex = exchangeRateTrendService.getExchangeRate(date, baseCurrency, targetCurrency);
		return new ResponseEntity<>(ex, HttpStatus.OK);
	}
}
