package com.app.component;

import com.app.model.ErrorMessage;
import com.app.model.EventPayLoad;
import com.app.model.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

@Component
public class RestClient {

    @Autowired
    private RestOperations restOperations;
    private final String url;
    private final Logger logger = LoggerFactory.getLogger(RestClient.class);
    @Value("${client.failure.error}")
    private String clienterror;
    @Value("${server.failure.error}")
    private String servererror;

    @Autowired
    public RestClient(@Value("${contract.service.url}") final String url){
        this.url = url;
    }

    /**
     * This Method purpose is to make API service call
     * and process the response if success returns the PAYLOAD DTO
     * If response is un-success sets ERRORMESSAGE DTO and Handles
     * the exception URI will be constructed dynamically based out
     * of request parameters DTO (EventRequest) which is configured
     * in application.properties file
     * @param eventRequest
     * @param errorMessage
     * @return
     */
    public EventPayLoad[] getRepoEventDetails(EventRequest eventRequest,ErrorMessage errorMessage){

        EventPayLoad[] eventPayLoads ={};
        try{
            eventPayLoads = restOperations.getForObject(url,EventPayLoad[].class,eventRequest.getOwner(),eventRequest.getRepo());
            return eventPayLoads;
        }catch (HttpStatusCodeException clientException){
            if(clientException.getStatusCode().is4xxClientError()){
               errorMessage.setStatuscode(clientException.getStatusCode().value());
               errorMessage.setMessage(clienterror);
               errorMessage.setError(true);
               logger.error(String.format(" Request Process is Un-Successful Status Code %1d",clientException.getStatusCode().value()));
          }else if(clientException.getStatusCode().is5xxServerError()){
               errorMessage.setStatuscode(clientException.getStatusCode().value());
               errorMessage.setMessage(servererror);
               errorMessage.setError(true);
                logger.error(String.format(" Request Process is Un-Successful Status Code %1d",clientException.getStatusCode().value()));
            }
       }catch (Exception exception){
            logger.error(exception.getMessage());
            errorMessage.setError(true);
            errorMessage.setMessage(servererror);
       }
        return eventPayLoads;
    }
}
