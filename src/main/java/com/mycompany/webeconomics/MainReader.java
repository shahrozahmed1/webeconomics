/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webeconomics;

import com.github.sh0nk.matplotlib4j.Plot;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainReader {
// PracBook, train
    
    private static final String FILE = "we_data/validation.csv";
    private static List<Impression> impressionList = new ArrayList<>();

    public static void main(String[] args) {
        try {

            Reader reader = Files.newBufferedReader(Paths.get(FILE));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextRecord;
            int counter = 0;

            while ((nextRecord = csvReader.readNext()) != null) {
                if (counter == 0) {
                    counter = 1;
                    continue;
                }
                Impression entry = new Impression(nextRecord);

                impressionList.add(entry);
                counter++;
            }

            // distinguish unique advertiser
            HashSet<Integer> advertisers = new HashSet<>();
            for (Impression impression : impressionList) {
                advertisers.add(impression.getAdvertiser());
                //... etc
            }

            // print statistics 
            printStats(advertisers);
            
            // printWeekdayCtr(advertisers);

            System.out.println("-------------------");
            System.out.println();

            printWeekdayCtr2(advertisers);

        } catch (IOException e) {

        }

    }

    public static void printStats(HashSet<Integer> advertisers) {

        // map of adv and its relevant data
        HashMap<Integer, Stats> map = new HashMap<>();

        for (int adv : advertisers) {

            // constructing new timeStat everytime for each advertiser
            Stats stat = new Stats();
            for (Impression imp : impressionList) {

                if (imp.getAdvertiser() == adv) {

                    stat.setAdvertiser(adv);

                    // count number of impression 
                    stat.increImpressionCount();

                    // count number of clicks
                    if (imp.isClicked()) {
                        stat.increClickCount();
                    }

                    // add paid price to cost
                    double pricePerAdv = (double) imp.getPayPrice() / 1000.0;
                    stat.addCost(pricePerAdv);

                }
            }

            // Update calculations
            stat.calcAll();

            // insert data in to hashmap
            map.put(adv, stat);

        }
        
        String csv = "Adv,Imps,Clicks,Cost,CTR,CPM,eCPC,\n";
        
        // Imps, Clicks, Cost, CTR, CPM, eCPC
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                "Adv.", "Imps", "Clicks",
                "Cost", "CTR", "CPM", "eCPC");

        System.out.println("-----------------------------------------"
                + "-------------------------------");

        for (Stats st : map.values()) {
            st.printStats();
            csv += st.downloadableStr();
        }
        
        try {
            downloadCSV(csv, "BStats.csv");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    // not using this one
    public static void printWeekdayCtr(HashSet<Integer> advertisers) {

        HashMap<Integer, TimeStats> statsMap = new HashMap<>();

        for (int adv : advertisers) {

            TimeStats timeStat = new TimeStats();

            // for each ipression
            for (Impression imp : impressionList) {

                // check impression
                if (imp.getAdvertiser() == adv) {

                    timeStat.setAdvertiser(adv);

                    // count number of impression 
                    timeStat.increImpressionCount();

                    // count number of clicks
                    if (imp.isClicked()) {
                        timeStat.increClickCount();

                        // get day and insert 
                        BasicTime time = imp.getTime();
                        timeStat.calculateWeekdayCTR(time.getDay());
                    }

                    // add paid price to cost
                    double pricePerAdv = (double) imp.getPayPrice() / 1000.0;
                    timeStat.addCost(pricePerAdv);

                }
            }

            // Do not update calculations again?
            timeStat.calculateAvg();
            // timeStat.calcAll();

            // insert data in to hashmap
            statsMap.put(adv, timeStat);

        }

        // Imps, Clicks, Cost, CTR, CPM, eCPC
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "Adv.", "1", "2", "3",
                "4", "5", "6", "7");

        System.out.println("-----------------------------------------"
                + "-------------------------------");

        for (Stats st : statsMap.values()) {
            st.printStats();
        }
      

    }

    public static void printWeekdayCtr2(HashSet<Integer> advertisers) {

        HashMap<Integer, TimeStats> statsMap = new HashMap<>();

        for (int adv : advertisers) {

            TimeStats timeStat = new TimeStats();

            // for each ipression
            for (Impression imp : impressionList) {

                // check impression
                if (imp.getAdvertiser() == adv) {
                    timeStat.setAdvertiser(adv);
                    // count number of impression 
                    // timeStat.increImpressionCount();

                    // count number of clicks
                    if (imp.isClicked()) {
                        timeStat.updateWeekdayCTR(imp.getTime().getDay(), true);
                        timeStat.updateHourCTR(imp.getTime().getHour(), true);
                    } else {
                        timeStat.updateWeekdayCTR(imp.getTime().getDay(), false);
                        timeStat.updateHourCTR(imp.getTime().getHour(), false);
                    }

                    // add paid price to cost
                    double pricePerAdv = (double) imp.getPayPrice() / 1000.0;

                }
            }

            // Do not update calculations again?
            //timeStat.calculateAvg();
            timeStat.calcAll();

            // insert data in to hashmap
            statsMap.put(adv, timeStat);

        }
        
        String csv = "1,2,3,4,5,6,7,\n";

        // Imps, Clicks, Cost, CTR, CPM, eCPC
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "Adv.", "1", "2", "3",
                "4", "5", "6", "7");

        System.out.println("-----------------------------------------"
                + "-------------------------------");

        for (TimeStats st : statsMap.values()) {
            st.printStats();
            csv += st.downloadableWeeklyStr();
        }
        
          
        try {
            downloadCSV(csv, "Weekly.csv");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        System.out.println();
        
        csv = "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23\n";

        // Imps, Clicks, Cost, CTR, CPM, eCPC
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s"
                + "%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s"
                + "%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "Adv.", "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23");

        System.out.println("-----------------------------------------"
                + "-------------------------------");

        for (TimeStats st : statsMap.values()) {
            st.printHoursStats();
            csv += st.downloadableHoursStr();
        }
        
        try {
            downloadCSV(csv, "Hourly.csv");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void printUserAgent(HashSet<String> userAgent) {

        HashMap<String, User> statsMap = new HashMap<>();

        for (String agent : userAgent) {

            Stats stat = new Stats();
            User user = new User();

            // for each ipression
            for (Impression imp : impressionList) {

                if (imp.getUser().getUserAgent().equals(agent)) {

                    // add imps to user
                    user.setUserAgent(agent);

                    // count number of impression 
                    stat.increImpressionCount();

                    // count number of clicks
                    if (imp.isClicked()) {
                        stat.increClickCount();
                    }

                    // add paid price to cost
                    double pricePerAdv = (double) imp.getPayPrice() / 1000.0;
                    stat.addCost(pricePerAdv);

                    // add clicks to users             
                    // update function    
                }
            }

            // insert data in to hashmap
            user.insertAgentMap(agent, stat);
        }

    }

    private static double getMeanBidPrice() {
        double meanBidPrice = -1;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(FILE));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextRecord;
            double i = 0;
            double totalBidPrice = 0;

            HashMap<Double, Integer> map = new HashMap<>();

            csvReader.readNext();// skip header
            while ((nextRecord = csvReader.readNext()) != null) {
                Impression entry = new Impression(nextRecord);
                if (entry.getBidPrice() > -1) {//just to be safe
                    totalBidPrice += entry.getBidPrice();
                    i++;
                }
            }

            meanBidPrice = totalBidPrice / i;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return meanBidPrice;
    }
    
        public static void downloadCSV(String outputStr, String filename) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File("/Users/shahrozahmed/Desktop/we_data/" + filename))) {
            StringBuilder sb = new StringBuilder();
            sb.append(outputStr);
            
            pw.write(sb.toString());
            
        }
    }
    

}

/*


CTR = 0
CPC = 0
Clicks = 0
Win = 0
Loss = 0
Budget = 6250
MyBids = 0
for payprice,click,prob,myBid in data_val[['PayPrice','click','myProb','myBid']].values:
    payprice = payprice/1000
    myBid = myBid/1000
    if myBid>=payprice and Budget >=payprice:
        Win = Win + 1
        Budget = Budget - payprice
        Clicks = Clicks + click
        MyBids = MyBids + myBid
    else:
        Loss = Loss + 1
CTR = (Clicks/Win)*100
CTR0 = (data_val['click'].values.sum()/len(data_val))*100
Budget0 = data_val['payprice'].values.sum()/1000


# In[51]:

print("CTR:",CTR,"Orginal:",CTR0)
print("Budget Left:",6250 - Budget,"Orginal:",Budget0)
print("Wins:",Win,"MyBids:",MyBids,"Clicks:",Clicks) # was 0.51933064050779
 */
