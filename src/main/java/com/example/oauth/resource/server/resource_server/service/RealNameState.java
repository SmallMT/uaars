package com.example.oauth.resource.server.resource_server.service;

public enum RealNameState {

    pass("通过"), // 企业新闻
    notPass("不通过");// 行业新闻


    private String state;

    RealNameState(String s) {
        this.state=s;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
