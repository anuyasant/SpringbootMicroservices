package com.ags.learn.microservices.springbootmicroservicesFE.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateTrendDTO {
	private String baseCurrency;
	private String targetCurrency;
	private Map<String, Double> trend;

}
