/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webeconomics;

import java.util.HashMap;

/**
 *
 * @author dani_
 */
public class User {
    private String userId;
    private String userAgent;
    // private ArrayList<String> userAgentList
    
    private String ip;
    private int region;
    private int city;
    private String tag;
    
    HashMap<String, Stats> statsMap;

    public User(String id, String uAgent, String ip, int region, int city, String tag){
        this.userId = id;
        this.userAgent = uAgent;
        this.ip = ip;
        this.region = region;
        this.city = city;
        this.tag = tag;
    }
    
    // constructor for the main
    public User() {
        statsMap = new HashMap<>();
    }
    
    
    public void insertAgentMap(String agent, Stats stat) {
        statsMap.put(agent, stat);
               
    }
    
    
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    
    

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userAgent=" + userAgent + ", ip=" + ip + ", region=" + region + ", city=" + city + ", tag=" + tag + '}';
    }
    
    
}

