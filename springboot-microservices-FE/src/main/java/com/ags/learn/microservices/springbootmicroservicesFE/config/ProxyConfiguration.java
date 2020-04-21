package com.ags.learn.microservices.springbootmicroservicesFE.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ags.learn.microservices.springbootmicroservicesFE.dto.ExchangeRateDTO;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name="ex-rate-app" /*, url="localhost:8000"*/)
public interface ProxyConfiguration {
	@GetMapping("/api/{fromDate}/{toDate}/{fromCurrency}/{toCurrency}")
	public Map<String, Double> retrieveExRateTrend
	(		@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate,
			@PathVariable("fromCurrency") String fromCurrency, 
			@PathVariable("toCurrency") String toCurrency);

	@GetMapping("/api/{date}/{fromCurrency}/{toCurrency}")
	public ExchangeRateDTO getExchangeRate
	(		@PathVariable("date") String date,
			@PathVariable("fromCurrency") String fromCurrency, 
			@PathVariable("toCurrency") String toCurrency);
}
