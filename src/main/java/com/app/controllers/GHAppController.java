package com.app.controllers;

import com.app.component.RestClient;
import com.app.model.ErrorMessage;
import com.app.model.EventPayLoad;
import com.app.model.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GHAppController {

    @Autowired
    private RestClient restClient; // rest client to make api calls
    @Autowired
    private EventRequest eventRequest; // holds request attributes
    @Autowired
    private ErrorMessage errorMessage; // holds error responses
    private Logger logger = LoggerFactory.getLogger(GHAppController.class);

    @PostMapping("/getevents")
    public String retrieveGHEventPayLoad(@ModelAttribute EventRequest eventRequest, Model model){

        // if request contains empty values which are required , this validation skips the API Call
        if(validateRequestParameters(eventRequest)){
            logger.info("Request parameters has empty values");
            errorMessage.setMessage(" Request parameters can't be empty , please provide valid values ");
            model.addAttribute("errormessage",errorMessage);
            return "error";
        }

        logger.info(String.format("GET Method retrieveGHEventPayLoad Request Parameters " +
                " are : %1$s, %2$s ", eventRequest.getOwner(), eventRequest.getRepo()));
        EventPayLoad[] EventPayLoadList = restClient.getRepoEventDetails(eventRequest,errorMessage);
        // Filter by event type collect all the matching payloads
        List<EventPayLoad> eventPayLoads = Arrays.asList(EventPayLoadList).stream()
                .filter(eventPayLoad -> eventPayLoad.getType().equals(eventRequest.getEventType()))
                .collect(Collectors.toList());
        model.addAttribute("errormessage",errorMessage);
        model.addAttribute("eventtype",eventRequest.getEventType());
        model.addAttribute("eventsList",eventPayLoads);

        if(errorMessage.isError()){
            logger.error("Request Process in Un-Success");
            return "error"; // view to handle errors
        }
        logger.info("Successfully processed request : "+eventRequest.toString());
        return "githubeventresults"; // view to handle successful responses
    }

    @GetMapping("/welcome")
    public String getRequest(Model model){
        model.addAttribute("request", eventRequest);
        return "welcome"; // view name
    }

    /**
     * Utility Method to check whether request has any
     * null values if any attribute is null , returns false
     */
    private boolean validateRequestParameters(EventRequest eventRequest){
        if(StringUtils.isEmpty(eventRequest.getRepo() ) || StringUtils.isEmpty(eventRequest.getOwner())){
            return true;
        }
        return false;
    }

}
