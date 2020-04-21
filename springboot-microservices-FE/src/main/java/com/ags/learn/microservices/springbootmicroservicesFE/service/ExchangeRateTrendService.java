package com.ags.learn.microservices.springbootmicroservicesFE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ags.learn.microservices.springbootmicroservicesFE.config.ProxyConfiguration;
import com.ags.learn.microservices.springbootmicroservicesFE.dao.ExchangeRateTrendDao;
import com.ags.learn.microservices.springbootmicroservicesFE.dto.ExchangeRateDTO;
import com.ags.learn.microservices.springbootmicroservicesFE.dto.ExchangeRateTrendDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateTrendService {

	@Autowired
	ProxyConfiguration proxyConfiguration;
	//call other microservice

	@Autowired
	ExchangeRateTrendDao exchangeRateTrendDao;


	public ExchangeRateTrendDTO retrieveExRateTrend(String fromCurrency, 
			String toCurrency,
			String fromDate,
			String toDate){

		ExchangeRateTrendDTO exchangeRateTrend = new ExchangeRateTrendDTO();
		exchangeRateTrend.setBaseCurrency(fromCurrency);
		exchangeRateTrend.setTargetCurrency(toCurrency);
		exchangeRateTrend.setTrend(
				proxyConfiguration.retrieveExRateTrend
				(fromDate, toDate, fromCurrency, toCurrency));
		return exchangeRateTrend;
	}
	
	public ExchangeRateDTO getExchangeRate(String date , 
			String fromCurrency, 
			String toCurrency){

		ExchangeRateDTO exchangeRateDTO = 
				proxyConfiguration.getExchangeRate(date, fromCurrency, toCurrency);
		return exchangeRateDTO;
	}

}
