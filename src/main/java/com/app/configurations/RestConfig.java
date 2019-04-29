package com.app.configurations;


import com.app.model.ErrorMessage;
import com.app.model.EventRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    // EventRequest Bean Declaration
    @Bean
    public EventRequest getEventRequest(){
        return new EventRequest();
    }

    // ErrorMessage Bean Declaration
    @Bean
    public ErrorMessage getErrorMessage(){
        return new ErrorMessage();
    }

    // RestOperation Bean Declaration
    @Bean
    public RestOperations createRestTemplate(final ClientHttpRequestFactory clientHttpRequestFactory){
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory createClientHttpRequestFactory(@Value("${connect.timeout}") final int connecttimeout,
                    @Value("${read.timeout}") final int readtimeout){
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setConnectTimeout(connecttimeout);
        httpComponentsClientHttpRequestFactory.setReadTimeout(readtimeout);
        return httpComponentsClientHttpRequestFactory;
    }
}
