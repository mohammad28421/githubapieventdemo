package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * SAMPLE EVENT PAYLOAD
 * {
 *         "id": "9515419470",
 *         "type": "CreateEvent",
 *         "actor": {
 *             "id": 34410866,
 *             "login": "mohammad28421",
 *             "display_login": "mohammad28421",
 *             "gravatar_id": "",
 *             "url": "https://api.github.com/users/mohammad28421",
 *             "avatar_url": "https://avatars.githubusercontent.com/u/34410866?"
 *         },
 *         "repo": {
 *             "id": 183562187,
 *             "name": "mohammad28421/githubapieventdemo",
 *             "url": "https://api.github.com/repos/mohammad28421/githubapieventdemo"
 *         },
 *         "payload": {
 *             "ref": "api_demo_work",
 *             "ref_type": "branch",
 *             "master_branch": "master",
 *             "description": null,
 *             "pusher_type": "user"
 *         },
 *         "public": true,
 *         "created_at": "2019-04-26T05:31:15Z"
 *     }
 *     Only selected payloads values are mapped
 *     i.e. Event type , careated date , Actor details
 *     Remaining are ignored
 */
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
