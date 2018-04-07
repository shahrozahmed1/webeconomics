/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webeconomics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author shahrozahmed
 */
public class Stats {
    private int advertiser;
    private int impressionCount;
    private int clickCount;
    private double cost;
    private double CTR; //click-through rate
    private double avgCPM;
    private double eCPC;
    private int advertiserCount;
   
    
    public int getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(int advertiser) {
        this.advertiser = advertiser;
    }

    public int getImpressionCount() {
        return impressionCount;
    }

    public void setImpressionCount(int impressionCount) {
        this.impressionCount = impressionCount;
    }

    public void increImpressionCount(){
        this.impressionCount++;
    }
    
    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
    
    public void increClickCount(){
        this.clickCount++;
    }    

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public void addCost(double cost){
        this.cost += cost;
    }

    public double getCTR() {
        return CTR;
    }

    public void setCTR(double ctr) {
        this.CTR = ctr;
    }
    

    
    // Calculate CTR value
    public void calculateCTR() {
        int clicks = getClickCount();
        int imps = getImpressionCount();
        this.CTR = ((double)clicks/(double)imps) * 100;
    }
    

    public double getAvgCPM() {
        return avgCPM;
    }

    public void setAvgCPM(double avgCPM) {
        this.avgCPM = avgCPM;
    }
    
    // How much it costs for add to be seen 1000
    // Its the average because calculated by tots imps and costs
    public void calculateCPM() {
        double costToAdv = getCost();
        int imps = getImpressionCount();
        double cpm = costToAdv * 1000 / imps;
        this.avgCPM = cpm;
    }

    public double get_eCPC() {
        return eCPC;
    }

    public void set_eCPC(int eCPC) {
        this.eCPC = eCPC;
    }
    
    // total cost/clicks
    public void calculate_eCPC() {
        double costToAdv = getCost();
        int clicks = getClickCount();
        if(clicks == 0)
            this.eCPC = 0;
        else
            this.eCPC = (costToAdv / clicks);
    }
    
    public int getAdvertiserCount(){
        return this.advertiserCount;
    }
    
    public void setAdvertiserCount(int count){
        this.advertiserCount = count;
    }
    
    public void increAdvertisercount(){
        this.advertiserCount++;
    }
    
    
    public void calcAll() {
        
        calculateCTR();
        calculateCPM();
        calculate_eCPC();
        
    }
    
    
    public void printStats() {
        
        // Advs, Imps, num Clicks, Cost, CTR, avg CPM, eCPC
        System.out.printf("%-10s %-10s %-10s %-10.2f %-10.2f %-10.2f %-10.2f\n", 
                getAdvertiser(), getImpressionCount(), getClickCount(), 
                getCost(), getCTR(), getAvgCPM(), get_eCPC());
    }
    
    public String downloadableStr() {
        
        String output = getAdvertiser() + "," + getImpressionCount() + "," + getClickCount() + 
                "," + getCost() + "," + getCTR() + "," + getAvgCPM() + "," + get_eCPC() + "\n";
        
        
        return output;
    }
    

}