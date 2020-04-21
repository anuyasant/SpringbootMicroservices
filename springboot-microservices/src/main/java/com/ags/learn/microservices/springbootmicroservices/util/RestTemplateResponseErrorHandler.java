package com.ags.learn.microservices.springbootmicroservices.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    CustomErrorResponse customErrorResponse;
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        //ObjectMapper om = new ObjectMapper();
        //Map<String, String> map = om.readValue(httpResponse.getBody(), Map.class);

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
//            if(map.get("error").contains("Symbols")){
//                 throw new IOException("Invalid target currency");
//            }
//            if(map.get("error").contains("Base")){
//                throw new IOException("Invalid base currency ");
//            }
        }

        if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            try {
                throw new ClassNotFoundException();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
