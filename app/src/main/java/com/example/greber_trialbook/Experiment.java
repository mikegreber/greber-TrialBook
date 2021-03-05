package com.example.greber_trialbook;

import java.util.Calendar;
import java.util.Date;

/**
 * Holds all information for an Experiment.
 * Successes and failures implemented as int counts, having a Trial class would create
 * unnecessary overhead for the purposes of this application.
 */
public class Experiment {

    private Date date;
    private String description;
    private int successes, failures;

    public Experiment() {
        this.description = "";
        this.date = Calendar.getInstance().getTime();
    }


    public Date getDate() { return date; }

    public String getDateString() { return Format.date(getDate()); }

    public String getDescription() {
        return description;
    }

    public int getSuccesses() {
        return successes;
    }

    public int getFailures() {
        return failures;
    }

    public float getSuccessRate() {
        if (successes + failures == 0) return 0.0f;
        return (float) successes / (successes + failures) * 100;
    }


    public void setDate(Date date) { this.date = date; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuccesses(int successes) { this.successes = successes; }

    public void setFailures(int failures) { this.failures = failures; }


    public void addSuccess() {
        successes++;
    }

    public void addFailure() {
        failures++;
    }
}
