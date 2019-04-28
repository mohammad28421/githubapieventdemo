package com.app.component;

import com.app.model.ErrorMessage;
import com.app.model.EventPayLoad;
import com.app.model.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

@Component
public class RestClient {

    private final RestOperations restOperations;
    private final String apiserviceUrl;
    private final String suffix;
    private final Logger logger = LoggerFactory.getLogger(RestClient.class);

    public RestClient(final RestTemplateBuilder restTemplateBuilder,final RestClientProperties restClientProperties){
        this.restOperations = restTemplateBuilder
                .setConnectTimeout(restClientProperties.getConnecttimeout())
                .setReadTimeout(restClientProperties.getReadtimeout())
                .build();
        this.apiserviceUrl = restClientProperties.getApiserviceurl();
        this.suffix = restClientProperties.getSuffix();

    }

    public EventPayLoad[] sendRequest(EventRequest eventRequest, ErrorMessage errorMessage){
        String clientUrl = buildEndpoint(eventRequest,apiserviceUrl);
        EventPayLoad[] eventPayLoads ={};
        try{
            eventPayLoads = restOperations.getForObject(clientUrl, EventPayLoad[].class);
            return eventPayLoads;
        }catch (HttpStatusCodeException clientException){
            if(clientException.getStatusCode().is4xxClientError()){
                errorMessage.setStatuscode(clientException.getStatusCode().value());
                errorMessage.setMessage(clientException.getMessage());
                errorMessage.setError(true);
                logger.error(errorMessage.toString());
            }else if(clientException.getStatusCode().is5xxServerError()){
                errorMessage.setStatuscode(clientException.getStatusCode().value());
                errorMessage.setMessage(clientException.getMessage());
                errorMessage.setError(true);
                logger.error(errorMessage.toString());
            }
        }catch (Exception exception){
            logger.error(exception.getMessage());
        }

        return eventPayLoads;
    }

    private String buildEndpoint(EventRequest eventRequest,String endPoint){
        StringBuilder endPointBuilder = new StringBuilder(endPoint);
        endPointBuilder.append(eventRequest.getOwner().trim()).append("/").append(eventRequest.getRepo().trim()).append("/").append(suffix);
        return endPointBuilder.toString();
    }

}
