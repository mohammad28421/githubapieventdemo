package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventPayLoad {

    private String type;
    private ActorPayload actor;
    private Date created_at;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ActorPayload getActor() {
        return actor;
    }

    public void setActor(ActorPayload actor) {
        this.actor = actor;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString(){
        return " Event Type = "+type+" ,  Actor Details = [ "+actor+" ] ,  Created Date = "+created_at +". ";
    }
}
