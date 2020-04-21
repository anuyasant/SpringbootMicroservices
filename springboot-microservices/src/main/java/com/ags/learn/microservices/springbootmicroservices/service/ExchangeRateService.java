package com.ags.learn.microservices.springbootmicroservices.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ags.learn.microservices.springbootmicroservices.dto.ExchangeRateDTO;
import com.ags.learn.microservices.springbootmicroservices.serviceClient.ExchangeRateServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExchangeRateService {

	@Autowired
	ExchangeRateServiceClient exchangeRateServiceClient;

	@Value("${exchangerate.ui.dateformat}")
	private String uiDateFormat;

	@Value("${exchangerate.client.dateformat}")
	private String clientDateFormat;

	@Value("${exchangerate.report.dateformat}")
	private String reportDateFormat;

	public ExchangeRateDTO getExchangeRate(String date, String baseCurrency, String targetCurrency) throws Exception{
		ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
		try {
			String result = exchangeRateServiceClient.getExchangeRate
					(convertDate(convertToDate(date)), baseCurrency, targetCurrency);
			exchangeRateDTO.setBaseCurrency(baseCurrency);
			exchangeRateDTO.setDate(date);
			exchangeRateDTO.setTargetCurrency(targetCurrency);
			exchangeRateDTO.setRate(extractExchangeRate(result));
		} catch (ParseException e) {
			log.info(e.getMessage());
		}
		return exchangeRateDTO;
	}

	@SuppressWarnings("unchecked")
	private Double extractExchangeRate(String s) throws Exception {
		Double exRate = 0.00D;
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Map<String,Map<String,Double>>> JSONtoMap = objectMapper.readValue(s, Map.class);
		Map<String,Map<String,Double>> dateCurrencyExRate = JSONtoMap.get("rates");
		for (Map.Entry<String, Map<String,Double>> entry : dateCurrencyExRate.entrySet()) {
			Map<String,Double> currencyExRate = entry.getValue();
			for (Map.Entry<String,Double> entry1 : currencyExRate.entrySet()) {
				exRate = entry1.getValue();
			}
		}
		return exRate;
	}


	public Map<String, Double> getExRateTrend(String baseCurrency, String targetCurrency, String fromDate, String toDate) 
			throws Exception {
		
		Map<String, Double> exchangeRateTrendMap = new HashMap<String, Double>();
		String result = exchangeRateServiceClient.getExchangeRateTrend
				(baseCurrency, targetCurrency, convertDate(convertToDate(fromDate)), 
						convertDate(convertToDate(toDate)));

		exchangeRateTrendMap = extractExchangeRateTrend(result).entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, 
						Map.Entry::getValue,(oldValue, newValue) -> 
				oldValue, LinkedHashMap::new));


//		List<ExchangeRateDTO> exchangeRateTrend = new ArrayList<ExchangeRateDTO>();
//		for (Entry<String, Double> entry : exchangeRateTrendMap.entrySet()) {
//			ExchangeRateDTO ex = new ExchangeRateDTO();
//			ex.setBaseCurrency(baseCurrency.toUpperCase());
//			ex.setTargetCurrency(targetCurrency.toUpperCase());
//			ex.setDate(entry.getKey());
//			ex.setRate(entry.getValue());
//			exchangeRateTrend.add(ex);
//		}

		return exchangeRateTrendMap;
	}

	private Map<String, Double> extractExchangeRateTrend(String result) {
		Map<String, Double> exchangeRateTrend = new HashMap<String, Double>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			@SuppressWarnings("unchecked")
			Map<String,Map<String,Map<String,Double>>> JSONtoMap = objectMapper.readValue(result, Map.class);
			Map<String,Map<String,Double>> dateCurrencyExRate = JSONtoMap.get("rates");
			for (Map.Entry<String, Map<String,Double>> entry : dateCurrencyExRate.entrySet()) {
				Map<String,Double> currencyExRate = entry.getValue();
				for (Map.Entry<String,Double> entry1 : currencyExRate.entrySet()) {
					exchangeRateTrend.put(entry.getKey(), entry1.getValue());
				}

			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exchangeRateTrend;
	}

	private Date convertToDate(String strDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(uiDateFormat);
		Date date = simpleDateFormat.parse(strDate); 
		return date;
	}

	private String convertDate(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(clientDateFormat);
		// format the date into another format
		return  simpleDateFormat.format(date);

	}
}
