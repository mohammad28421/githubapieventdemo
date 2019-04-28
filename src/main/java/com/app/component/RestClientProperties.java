package com.app.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties("rest.client")
public class RestClientProperties {

    private Duration connecttimeout;
    private Duration readtimeout;
    private String apiserviceurl;
    private String suffix;

    public Duration getConnecttimeout() {
        return connecttimeout;
    }

    public void setConnecttimeout(Duration connecttimeout) {
        this.connecttimeout = connecttimeout;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Duration getReadtimeout() {
        return readtimeout;
    }

    public void setReadtimeout(Duration readtimeout) {
        this.readtimeout = readtimeout;
    }

    public String getApiserviceurl() {
        return apiserviceurl;
    }

    public void setApiserviceurl(String apiserviceurl) {
        this.apiserviceurl = apiserviceurl;
    }
}
