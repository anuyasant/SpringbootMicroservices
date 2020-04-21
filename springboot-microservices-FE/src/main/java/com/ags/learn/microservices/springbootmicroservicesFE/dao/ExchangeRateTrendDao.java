package com.ags.learn.microservices.springbootmicroservicesFE.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ExchangeRateTrendDao {

	private Map<String, Double> exRateTrend;
	
	
	public Map<String, Double> getExchangeRateTrend(){
		return this.exRateTrend;  
	}
	
	public void setExchangeRateTrend(Map<String, Double> exchangeRateTrend){
		 this.exRateTrend = exchangeRateTrend;
	}
}
