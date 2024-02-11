package edu.java.bot.users;

import edu.java.bot.model.SessionState;
import java.net.URI;
import java.util.List;

public class User {
    private Long id;
    private List<URI> sites;

    public SessionState getState() {
        return state;
    }

    public void setState(SessionState state) {
        this.state = state;
    }

    private SessionState state;

    public User(Long id, List<URI> sites, SessionState state) {
        this.id = id;
        this.sites = sites;
        this.state = state;

    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<URI> getSites() {
        return sites;
    }

    public void setSites(List<URI> sites) {
        this.sites = sites;
    }
}
