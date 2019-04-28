package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Sample Actor Payload
 *"actor": {
 *             "id": 34410866,
 *             "login": "mohammad28421",
 *             "display_login": "mohammad28421",
 *             "gravatar_id": "",
 *             "url": "https://api.github.com/users/mohammad28421",
 *             "avatar_url": "https://avatars.githubusercontent.com/u/34410866?"
 *         }
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorPayload {

    private Long id;
    private String login;
    private String display_login;
    private String gravatar_id;
    private String url;
    private String avatar_url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDisplay_login() {
        return display_login;
    }

    public void setDisplay_login(String display_login) {
        this.display_login = display_login;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public String toString(){
        return " ID = "+id.toString()+" ,  Login Name = "+login+" ,  Display Login = "+display_login+" ";
    }
}
