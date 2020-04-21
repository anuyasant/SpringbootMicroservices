package com.ags.learn.microservices.springbootmicroservices.serviceClient;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ags.learn.microservices.springbootmicroservices.util.RestTemplateResponseErrorHandler;
import lombok.NoArgsConstructor;



@Service
@NoArgsConstructor
public class ExchangeRateServiceClient {

	//integrate
	//https://api.exchangeratesapi.io/history?start_at=2018-01-17&end_at=2018-01-17&symbols=GBP&base=USD

	private final String EXCHANGE_RATE_URL = "https://api.exchangeratesapi.io/";
	private final String DATE_PARAMS = "history?";
	private final String START_DATE_PARAM = "start_at=";
	private final String END_DATE_PARAM = "&end_at=";
	private final String TARGET_CCY_PARAM = "&symbols=";
	private final String BASE_CCY_PARAM = "&base=";

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public ExchangeRateServiceClient(RestTemplateBuilder restTemplateBuilder){
		restTemplate = restTemplateBuilder.
				errorHandler(new RestTemplateResponseErrorHandler())
				.build();
	}

//	private ClientHttpRequestFactory clientHttpRequestFactory() {
//	    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//	    factory.setReadTimeout(15000);
//	    factory.setConnectTimeout(15000);
//	    return factory;
//	}

//	public static void main(String[] args) {
//		ExchangeRateServiceClient exchangeRateServiceClient = new ExchangeRateServiceClient();
//		try {
//			exchangeRateServiceClient.getExchangeRateTrend("INR", "USD", "2018-01-01", "2018-01-31");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public String getExchangeRate(String date, String baseCurrency, String targetCurrency) throws Exception {
		String completeURL = generateCompleteURL(date, date, baseCurrency, targetCurrency);
		@SuppressWarnings("unused")
		String resultUsingGetForObject = getExchangeRateUsingGetForObject(completeURL);
		String resultBySettingHeardersUsingExchange = getExchangeRateBySettingHeardersUsingExchange(completeURL);
		return resultBySettingHeardersUsingExchange;
	}

	public String getExchangeRateTrend(String baseCurrency, String targetCurrency, String fromDate,
			String toDate) throws Exception {
		String completeURL = generateCompleteURL(fromDate, toDate, baseCurrency, targetCurrency);
		@SuppressWarnings("unused")
		String resultUsingGetForObject = getExchangeRateUsingGetForObject(completeURL);
		String resultBySettingHeardersUsingExchange = getExchangeRateBySettingHeardersUsingExchange(completeURL);
		return resultBySettingHeardersUsingExchange;
	}


	


	private String getExchangeRateBySettingHeardersUsingExchange(String completeURL) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> exchangeRate = restTemplate.exchange(completeURL, HttpMethod.GET, entity, String.class);

		if(exchangeRate.getBody().contains("Symbols")){
			throw new Exception("Invalid target currency! "+ exchangeRate.getStatusCode());
		}
		if(exchangeRate.getBody().contains("Base")){
			throw new Exception("Invalid base currency! "+ exchangeRate.getStatusCode());
		}
		return exchangeRate.getBody();
	}


	private String getExchangeRateUsingGetForObject(String url) {
		return restTemplate.getForObject(url, String.class);
	}

	private String generateCompleteURL(String fromDate,String toDate, String baseCurrency, String targetCurrency) {

		return EXCHANGE_RATE_URL + DATE_PARAMS + 
				START_DATE_PARAM + fromDate +
				END_DATE_PARAM + toDate +
				TARGET_CCY_PARAM + targetCurrency.toUpperCase() +
				BASE_CCY_PARAM + baseCurrency.toUpperCase();
	}
}
