package com.example.oauth.resource.server.resource_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bind_agent", schema = "uaa", catalog = "")
public class BindAgent {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnore
    private User user;


    @ManyToOne
    private BindEnterprise bindEnterprise;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BindEnterprise getBindEnterprise() {
        return bindEnterprise;
    }

    public void setBindEnterprise(BindEnterprise bindEnterprise) {
        this.bindEnterprise = bindEnterprise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BindAgent that = (BindAgent) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
