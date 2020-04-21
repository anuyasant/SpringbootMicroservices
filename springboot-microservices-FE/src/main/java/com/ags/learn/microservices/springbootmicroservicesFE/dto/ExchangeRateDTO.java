package com.ags.learn.microservices.springbootmicroservicesFE.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ExchangeRateDTO {
	private String baseCurrency;
	private String targetCurrency;
	private String date;
	private Double rate;
}
