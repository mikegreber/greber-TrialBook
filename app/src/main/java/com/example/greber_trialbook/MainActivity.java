package com.example.greber_trialbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Handles GUI for displaying the list of all experiments.
 * Clicking on an experiment opens an ExperimentActivity.
 * Deleting experiments (swipe to delete) handled by ItemSwipeHelper and ExperimentAdapter.
 * Action button opens an ExperimentFragment for creating a new experiment.
 */
public class MainActivity extends AppCompatActivity {

    public static final String OPENED_EXPERIMENT = "com.mainactivity.OPENED_EXPERIMENT";

    private ExperimentListAdapter experimentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Experiments");

        experimentAdapter = new ExperimentListAdapter(this::openExperiment);

        RecyclerView recyclerView = findViewById(R.id.experiment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(experimentAdapter);

        ItemSwipeHelper.setCallback(recyclerView, (index, direction) -> {
            DataManager.removeExperiment(index);
            experimentAdapter.notifyItemRemoved(index); });
    }

    private void openExperiment(int experimentIndex) {
        Intent intent = new Intent(this, ExperimentActivity.class);
        intent.putExtra(OPENED_EXPERIMENT, experimentIndex);
        startActivity(intent);
    }

    public void onAddClicked(View view) {
        ExperimentFragment fragment = new ExperimentFragment.Short("Add Experiment", experiment -> {
            DataManager.addExperiment(experiment);
            experimentAdapter.notifyDataSetChanged();
        });

        fragment.show(getSupportFragmentManager(), "ADD_EXPERIMENT");
    }
}