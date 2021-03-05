package com.example.greber_trialbook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Activity to handle the GUI for displaying an Experiment and incrementing its trial results.
 * Displays date, description, total trials, success rate, success count, and fail count.
 * Provides buttons to easily add successes or failures in the activity.
 * Provides ability to edit all details of the experiment through an ExperimentFragment.
 *
 * Experiment is retrieved by an index sent through intent since sending an experiment through
 * the intent does not return a reference to the same object.
 */
public class ExperimentActivity extends AppCompatActivity {

    private Experiment experiment;
    private TextView descriptionTextView, totalTextView, successRateTextView, successesTextView, failuresTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        int experimentIndex = (int) getIntent().getSerializableExtra(MainActivity.OPENED_EXPERIMENT);
        experiment = DataManager.getExperiment(experimentIndex);

        initializeUI(experiment);
    }



    /**
     * Stack Overflow
     * author: Onur Cevik - https://stackoverflow.com/users/4436664/onur-%c3%87evik
     * title: How to create button in Action bar in android [duplicate]
     * date: October 10, 2019
     * link: https://stackoverflow.com/a/38159004
     * licence: CC BY-SA 4.0
     * Reference for both onCreateOptionsMenu and onOptionsItemSelected below
     */
    // Add edit button to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.experiment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // Edit button functionality -> open an ExperimentFragment
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.edit_experiment == item.getItemId()){
            new ExperimentFragment("Edit Experiment", experiment, e -> updateUI())
                    .show(getSupportFragmentManager(), "EDIT_EXPERIMENT");
        }
        return super.onOptionsItemSelected(item);
    }


    // Sets title and returns reference to experiment_info_value TextView
    private TextView initializeCard(String title, int id) {
        View view = findViewById(id);
        TextView titleTextView = view.findViewById(R.id.experiment_info_title);
        titleTextView.setText(title);
        return view.findViewById(R.id.experiment_info_value);
    }


    // Gets references and sets up UI text
    private void initializeUI(Experiment experiment) {
        setTitle(experiment.getDateString());

        descriptionTextView = findViewById(R.id.experiment_activity_description);
        descriptionTextView.setText(experiment.getDescription());

        totalTextView = initializeCard("Total Trials", R.id.experiment_total);
        successRateTextView = initializeCard("Success Rate",R.id.experiment_percentage);
        successesTextView = initializeCard("Successes",R.id.experiment_success);
        failuresTextView = initializeCard("Failures",R.id.experiment_fail);

        updateUI();
    }


    // Updates all experiment information in GUI
    private void updateUI() {
        setTitle(experiment.getDateString());
        descriptionTextView.setText(experiment.getDescription());
        successesTextView.setText(String.valueOf(experiment.getSuccesses()));
        failuresTextView.setText(String.valueOf(experiment.getFailures()));
        totalTextView.setText(String.valueOf(experiment.getSuccesses() + experiment.getFailures()));
        successRateTextView.setText(String.format("%.1f%%",experiment.getSuccessRate()));
    }


    // Increments success count
    public void onSuccessClicked(View view){
        experiment.addSuccess();
        updateUI();
    }


    // Increments fail count
    public void onFailClicked(View view){
        experiment.addFailure();
        updateUI();
    }

}