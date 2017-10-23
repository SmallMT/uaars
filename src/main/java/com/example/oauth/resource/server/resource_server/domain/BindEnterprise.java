package com.example.oauth.resource.server.resource_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bind_enterprise", schema = "uaa", catalog = "")
public class BindEnterprise {
    private int id;
    private String enterpriseName;
    private String creditCode;

    private User user;


    @JsonIgnore
    private List<BindAgent> bindAgentList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "enterprise_name")
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    @Basic
    @Column(name = "credit_code")
    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    @ManyToOne
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @OneToMany(mappedBy = "bindEnterprise")
    @JsonIgnore
    public List<BindAgent> getBindAgentList() {
        return bindAgentList;
    }

    public void setBindAgentList(List<BindAgent> bindAgentList) {
        this.bindAgentList = bindAgentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BindEnterprise that = (BindEnterprise) o;

        if (id != that.id) return false;
        if (enterpriseName != null ? !enterpriseName.equals(that.enterpriseName) : that.enterpriseName != null)
            return false;
        if (creditCode != null ? !creditCode.equals(that.creditCode) : that.creditCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (enterpriseName != null ? enterpriseName.hashCode() : 0);
        result = 31 * result + (creditCode != null ? creditCode.hashCode() : 0);
        return result;
    }
}
