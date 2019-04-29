package com.api.demo.app;

import com.app.component.RestClient;
import com.app.model.ErrorMessage;
import com.app.model.EventPayLoad;
import com.app.model.EventRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;
import java.util.Arrays;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RestClientTest {

    @Autowired
    private RestClient restClient;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${contract.service.url}")
    private String url;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private EventRequest eventRequest;
    @Autowired
    private ErrorMessage errorMessage;
    String owner ="owner";
    String repo ="repo";
    UriTemplateHandler uriTemplateHandler;
    String urlexpanded ;

    @Before
    public void setup(){
        this.mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        eventRequest.setOwner("owner");
        eventRequest.setRepo("repo");
        uriTemplateHandler = new DefaultUriBuilderFactory();
        urlexpanded = String.valueOf(uriTemplateHandler.expand(url,owner,repo));

    }

    /**
     * Unit Test verifies the Success API response
     * With Returned JSON String and the Event payload values
     */
    @Test
    public void getRepoEventDetailsTest_Success(){
        mockRestServiceServer.expect(requestTo(urlexpanded))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).body("    [\n" +
                        "    {\n" +
                        "        \"id\": \"9515419470\",\n" +
                        "        \"type\": \"CreateEvent\",\n" +
                        "        \"actor\": {\n" +
                        "            \"id\": 34410866,\n" +
                        "            \"login\": \"mohammad28421\",\n" +
                        "            \"display_login\": \"mohammad28421\",\n" +
                        "            \"gravatar_id\": \"\",\n" +
                        "            \"url\": \"https://api.github.com/users/mohammad28421\",\n" +
                        "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/34410866?\"\n" +
                        "        },\n" +
                        "        \"repo\": {\n" +
                        "            \"id\": 183562187,\n" +
                        "            \"name\": \"mohammad28421/githubapieventdemo\",\n" +
                        "            \"url\": \"https://api.github.com/repos/mohammad28421/githubapieventdemo\"\n" +
                        "        },\n" +
                        "        \"payload\": {\n" +
                        "            \"ref\": \"api_demo_work\",\n" +
                        "            \"ref_type\": \"branch\",\n" +
                        "            \"master_branch\": \"master\",\n" +
                        "            \"description\": null,\n" +
                        "            \"pusher_type\": \"user\"\n" +
                        "        },\n" +
                        "        \"public\": true,\n" +
                        "        \"created_at\": \"2019-04-26T05:31:15Z\"\n" +
                        "    }\n" +
                        "]").contentType(MediaType.APPLICATION_JSON));
        EventPayLoad[] eventPayLoads = restClient.getRepoEventDetails(eventRequest,errorMessage);
        mockRestServiceServer.verify();
        Arrays.asList(eventPayLoads).stream().forEach(eventPayLoad -> {
            Assert.assertEquals("CreateEvent",eventPayLoad.getType());
            Assert.assertEquals("mohammad28421",eventPayLoad.getActor().getLogin());
        });
    }

    /**
     * Unit Test verifies the Success API response
     * With Returned Empty JSON String and the Event payload values
     */
    @Test
    public void getRepoEventDetailsTest_Empty(){
        mockRestServiceServer.expect(requestTo(urlexpanded))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .body("[]").contentType(MediaType.APPLICATION_JSON));
        EventPayLoad[] eventPayLoads = restClient.getRepoEventDetails(eventRequest,errorMessage);
        mockRestServiceServer.verify();
        Assert.assertTrue(eventPayLoads.length<1);

    }

    /**
     * Unit Test verifies the UnSuccess NOT FOUND API response
     */
    @Test
    public void getRepoEventDetailsError_404(){
        mockRestServiceServer.expect(requestTo(urlexpanded))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .body("[]").contentType(MediaType.APPLICATION_JSON));
        EventPayLoad[] eventPayLoads = restClient.getRepoEventDetails(eventRequest,errorMessage);
        mockRestServiceServer.verify();
        Assert.assertTrue(errorMessage.isError());
        Assert.assertTrue(errorMessage.getStatuscode()==404);

    }

    /**
     * Unit Test verifies the UnSuccess INTERNAL SERVER ERROR API response
     */
    @Test
    public void getRepoEventDetailsError_500(){
        mockRestServiceServer.expect(requestTo(urlexpanded))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("[]").contentType(MediaType.APPLICATION_JSON));
        EventPayLoad[] eventPayLoads = restClient.getRepoEventDetails(eventRequest,errorMessage);
        mockRestServiceServer.verify();
        Assert.assertTrue(errorMessage.isError());
        Assert.assertTrue(errorMessage.getStatuscode()==500);
    }

}
