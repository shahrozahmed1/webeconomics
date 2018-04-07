/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webeconomics;

/**
 *
 * @author shahrozahmed
 */
public class TimeStats extends Stats {

    // time related fields
    private double[] weekdaysCTR = new double[7];

    private int[] WeekdaysCounter = new int[7];
    private int[] weekdaysClicks = new int[7];
    private int[] WeekdaysImps = new int[7];

    private double[] hoursCTR = new double[24];
    private int[] hoursClicks = new int[24];
    private int[] hoursImps = new int[24];

    public void setWeekdayCTR(int weekday, double value) {
        double currentVal = this.weekdaysCTR[weekday];
        this.weekdaysCTR[weekday] = currentVal + value;
    }

    public double[] getWeekdayCTR() {
        return this.weekdaysCTR;
    }

    public void setHoursCTR(int hour, double value) {
        double currentVal = this.hoursCTR[hour];
        this.hoursCTR[hour] = currentVal + value;
    }

    public double[] getHoursCTR() {
        return this.hoursCTR;
    }

    public void calculateWeekdayCTR(int weekday) {

        calculateCTR();
        int inc = WeekdaysCounter[weekday];
        WeekdaysCounter[weekday] = inc + 1;
        setWeekdayCTR(weekday, this.getCTR());

    }

    public void calculateAvg() {

        for (int i = 0; i < weekdaysCTR.length; i++) {

            setWeekdayCTR(i, weekdaysCTR[i] / WeekdaysCounter[i]);
        }

    }

    // ex 2
    public void updateWeekdayCTR(int weekday, Boolean clicked) {

        int imps = this.WeekdaysImps[weekday];
        this.WeekdaysImps[weekday] = imps + 1;

        if (clicked) {
            int clicks = this.weekdaysClicks[weekday];
            this.weekdaysClicks[weekday] = clicks + 1;
        }

    }

    public void updateHourCTR(int hour, Boolean clicked) {

        int imps = this.hoursImps[hour];
        this.hoursImps[hour] = imps + 1;

        if (clicked) {
            int clicks = this.hoursClicks[hour];
            this.hoursClicks[hour] = clicks + 1;
        }

    }

    // ex 2
    @Override
    public void calcAll() {

        // calculate for each weekday
        for (int i = 0; i < 7; i++) {

            int clicks = this.weekdaysClicks[i];
            int imps = this.WeekdaysImps[i];
            this.weekdaysCTR[i] = ((double) clicks / (double) imps) * 100;

        }

        // calculate for each hour
        for (int i = 0; i < 24; i++) {
            int clicks = this.hoursClicks[i];
            int imps = this.hoursImps[i];
            this.hoursCTR[i] = ((double) clicks / (double) imps) * 100;

        }
    }

    @Override
    public void printStats() {

        System.out.printf("%-15s %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f\n",
                this.getAdvertiser(),
                this.weekdaysCTR[0], this.weekdaysCTR[1], this.weekdaysCTR[2],
                this.weekdaysCTR[3], this.weekdaysCTR[4], this.weekdaysCTR[5],
                this.weekdaysCTR[6]);

    }

    public String downloadableWeeklyStr() {

        String output = this.getAdvertiser() + "," + this.weekdaysCTR[0] + "," + this.weekdaysCTR[1]
                + "," + this.weekdaysCTR[2] + "," + this.weekdaysCTR[3] + "," + this.weekdaysCTR[4] + ","
                + this.weekdaysCTR[5] + "," + this.weekdaysCTR[6] + "\n";

        return output;
    }

    public void printHoursStats() {

        System.out.printf("%-15s %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f"
                + "%-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f"
                + "%-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f %-15.5f\n",
                this.getAdvertiser(),
                this.hoursCTR[0], this.hoursCTR[1], this.hoursCTR[2],
                this.hoursCTR[3], this.hoursCTR[4], this.hoursCTR[5],
                this.hoursCTR[6], this.hoursCTR[7], this.hoursCTR[8], this.hoursCTR[9],
                this.hoursCTR[10], this.hoursCTR[11], this.hoursCTR[12],
                this.hoursCTR[13], this.hoursCTR[14], this.hoursCTR[15], this.hoursCTR[16],
                this.hoursCTR[17], this.hoursCTR[17], this.hoursCTR[18],
                this.hoursCTR[19], this.hoursCTR[20], this.hoursCTR[21],
                this.hoursCTR[22], this.hoursCTR[23]);

    }

    public String downloadableHoursStr() {

        String output = this.getAdvertiser() + ","
                + this.hoursCTR[0] + "," + this.hoursCTR[1] + "," + this.hoursCTR[2] + ","
                + this.hoursCTR[3] + "," + this.hoursCTR[4] + "," + this.hoursCTR[5] + ","
                + this.hoursCTR[6] + "," + this.hoursCTR[7] + "," + this.hoursCTR[8] + "," + this.hoursCTR[9] + ","
                + this.hoursCTR[10] + "," + this.hoursCTR[11] + "," + this.hoursCTR[12] + ","
                + this.hoursCTR[13] + "," + this.hoursCTR[14] + "," + this.hoursCTR[15] + "," + this.hoursCTR[16] + ","
                + this.hoursCTR[17] + "," + this.hoursCTR[18] + ","
                + this.hoursCTR[19] + "," + this.hoursCTR[20] + "," + this.hoursCTR[21] + ","
                + this.hoursCTR[22] + "," + this.hoursCTR[23] + "," + "\n";

        return output;
    }

}
