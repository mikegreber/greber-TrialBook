package com.example.greber_trialbook;

import java.util.ArrayList;

/**
 * Holds all experiments recorded in the application.
 * Started out with this class implemented as a Singleton, but changed the implementation to just
 * have a static ArrayList containing the experiments, with static methods to interact with it.
 * This functionally works the same as my initial Singleton implementation, but I no longer need to
 * call DataManager.get() before calling methods.
 */
public final class DataManager {

    // Holds all experiments recorded in the application
    private static ArrayList<Experiment> experiments;

    // private constructor to prevent instantiation
    private DataManager() {}

    public static ArrayList<Experiment> getExperiments() {
        if (experiments == null) experiments = new ArrayList<>();
        return experiments;
    }

    public static Experiment getExperiment(int index) {
        if (experiments == null) experiments = new ArrayList<>();
        return experiments.get(index);
    }

    public static void addExperiment(Experiment experiment) {
        if (experiments == null) experiments = new ArrayList<>();
        experiments.add(experiment);
    }

    public static void removeExperiment(int index) {
        if (experiments == null) experiments = new ArrayList<>();
        experiments.remove(index);
    }
}
