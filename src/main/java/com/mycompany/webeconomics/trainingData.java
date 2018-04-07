/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webeconomics;


public class trainingData {

    public int clicks;
    public int weekday;
    public int hour;
    public String bidId;
    public String userId;
    public String userAgent;
    public String IP;
    public int regionCode;
    public int cityCode;
    public int adexChange;
    public String domain;
    public String url;
    public String urlId;
    public String slotId;
    public int slotWidth;
    public int slotHeight;
    public String slotVisibility;
    public String slotFormat;
    public int slotPrice;
    public String creative;
    public int bidPrice;
    public int payPirce;
    public String keypage;
    public int advertiser;
    public String userTag;
    public int[] userTagIntArray;

    public trainingData(String[] nextRecord) {
        clicks = Integer.valueOf(nextRecord[0]);
        weekday = Integer.valueOf(nextRecord[1]);
        hour = Integer.valueOf(nextRecord[2]);
        bidId = nextRecord[3];
        userId = nextRecord[4];
        userAgent = nextRecord[5];
        IP = nextRecord[6];
        regionCode = Integer.valueOf(nextRecord[7]);
        cityCode = Integer.valueOf(nextRecord[8]);
        adexChange = Integer.valueOf(nextRecord[9]);
        domain = nextRecord[10];
        url = nextRecord[11];
        urlId = nextRecord[12];
        slotId = nextRecord[13];
        slotWidth = Integer.valueOf(nextRecord[14]);
        slotHeight = Integer.valueOf(nextRecord[15]);
        slotVisibility = nextRecord[16];
        slotFormat = nextRecord[17];
        slotPrice = Integer.valueOf(nextRecord[18]);
        creative = nextRecord[19];
        bidPrice = Integer.valueOf(nextRecord[20]);
        bidPrice = Integer.valueOf(nextRecord[21]);
        keypage = nextRecord[21];
        advertiser = Integer.valueOf(nextRecord[22]); // Category of advertiser
        userTag = nextRecord[23];
        userTagIntArray = userTagIntArray(userTag);
    }

    public final int[] userTagIntArray(String userTag) {

        String[] userTagStrArray = userTag.split(",");

        int[] intArray = new int[userTagStrArray.length];
        int i = 0;
        for (String item : userTagStrArray) {
            intArray[i] = Integer.parseInt(item);
            i++;
        }

        return intArray;
    }
    
    
    
    
    
    public int getAdviser() {
        return advertiser;
    }
    
    public int getClicks() {
        return clicks;
    }

    
    
    @Override
    public String toString() {
        return "Entry{" + "weekday=" + weekday + ", hour=" + hour + ", bidId=" + bidId + ", userId=" + userId + ", useragent=" + userAgent + ", IP=" + IP + ", region=" + regionCode + ", city=" + cityCode + ", adexchange=" + adexChange + ", domain=" + domain + ", url=" + url + ", urlId=" + urlId + ", slotId=" + slotId + ", slotWidth=" + slotWidth + ", slotHeight=" + slotHeight + ", slotVisibility=" + slotVisibility + ", slotFormat=" + slotFormat + ", slotPrice=" + slotPrice + ", creative=" + creative + ", keypage=" + keypage + ", advertiser=" + advertiser + ", userTag=" + userTag + '}';
    }

}

// get the advertiser category
// insert relevant objects within the list?
// .. advertiser
// period - 
// bids - double - bidId?
// imps - double - 
// clicks - int - 
// convs - int - 
// cost - double - 
// Win ratio - float - 
// CTR - float - Click Through Rate
// CVR - float - 
// CPM - float - 
// eCPC - float - 
// ------------------
// avgCTR = (train.click.sum() / train.logtype.sum()) 
// payprice = payprice/1000
// myBid = myBid/1000
// CTR0 = 'click'.values.sum()/len(data_val))*100
// 
// eCPC:",Budget/Clicks
// "Impressions:",Win
// "CPM:",(Budget/Win)*1000
// "eCPC:",Budget/Clicks)
// Spent:",Budget

// Dan - impressions, clicks, costs, CTR, CPM, eCPC
