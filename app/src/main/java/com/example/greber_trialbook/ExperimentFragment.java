package com.example.greber_trialbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Fragment for creating or editing experiments.
 * OnOkCallback interface executes when OK is pressed for calling code from the instantiating class.
 * Validates user input and notifies user if inputs are invalid.
 * Used with MainActivity and ExperimentActivity.
 *
 * Short static class to simplify creating the fragment without the inputs to edit success count and fail count.
 *
 * If an experiment is passed in, will modify that experiment and return a reference to it in OnOkCallback when OK is pressed.
 * If no experiment is passed in, will create a new experiment and return a reference to it in OnOkCallback when OK is pressed.
 */
public class ExperimentFragment extends DialogFragment {

    private String title;
    private EditText dateEditText, descriptionEditText, successCountEditText, failCountEditText;
    private Group allInputsGroup, trialInputGroup;
    private CalendarView calendar;
    private Date date;
    private Experiment experiment;
    private OnOkCallback callback;
    private InputMethodManager inputManager;

    private boolean showCalendar;
    private boolean shortForm;

    // Callback passed in through the constructor that will be called after OK is pressed
    // Returns reference to the experiment created or modified in this fragment
    public interface OnOkCallback {
        void onOkPressed(Experiment experiment);
    }

    // For fragment without option to edit successes and failures
    public static class Short extends ExperimentFragment {
        public Short(String title, OnOkCallback callback) {
            super(title, callback);
            super.shortForm = true;
        }

        public Short(String title, Experiment experiment, OnOkCallback callback) {
            super(title, experiment, callback);
            super.shortForm = true;
        }
    }

    // Generate new experiment when no experiment passed in
    public ExperimentFragment(String title, OnOkCallback callback) {
        this(title, new Experiment(), callback);
    }

    public ExperimentFragment(String title, Experiment experiment, OnOkCallback callback) {
        this.experiment = experiment;
        this.date = experiment.getDate();
        this.callback = callback;
        this.title = title;
    }


    // Setup and alert dialog and button callbacks
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_fragment_layout, null);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).setTitle(title).create();
        setupLayout(view);

        /**
         * Stack Overflow
         * author: Reto Meier - https://stackoverflow.com/users/822
         * title: How do you close/hide the Android soft keyboard using Java?
         * date: October 28, 2020
         * link: https://stackoverflow.com/a/1109108
         * license: CC BY-SA 4.0
         */
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        calendar = view.findViewById(R.id.date_calendarView);
        calendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            date = new GregorianCalendar(year, month, day).getTime();
            dateEditText.setText(Format.date(date));
        });


        // Set click listeners for cancel and OK buttons
        view.findViewById(R.id.button_cancel).setOnClickListener(i -> alertDialog.dismiss());
        view.findViewById(R.id.button_confirm).setOnClickListener(i -> {
            if (showCalendar) {
                toggleCalendar();
            } else if (checkInputsValid(true)) {
                experiment.setDate(date);
                experiment.setDescription(descriptionEditText.getText().toString());
                experiment.setSuccesses(Integer.parseInt(successCountEditText.getText().toString()));
                experiment.setFailures(Integer.parseInt(failCountEditText.getText().toString()));

                alertDialog.dismiss();
                if (callback != null) callback.onOkPressed(experiment);
            }
        });

        return alertDialog;
    }

    // Gets references sets default values for the inputs
    private void setupLayout(View view) {
        calendar = view.findViewById(R.id.date_calendarView);
        calendar.setDate(date.getTime());

        dateEditText = view.findViewById(R.id.experiment_date_editText);
        dateEditText.setText(experiment.getDateString());
        dateEditText.setFocusable(false);
        dateEditText.setOnClickListener(i -> toggleCalendar());

        descriptionEditText = view.findViewById(R.id.experiment_description_editText);
        descriptionEditText.setText(experiment.getDescription());

        successCountEditText = view.findViewById(R.id.success_count_editText);
        successCountEditText.setText(String.valueOf(experiment.getSuccesses()));

        failCountEditText = view.findViewById(R.id.fail_count_editText);
        failCountEditText.setText(String.valueOf(experiment.getFailures()));

        allInputsGroup = view.findViewById(R.id.all_inputs_group);
        trialInputGroup = view.findViewById(R.id.trial_inputs_group);

        calendar.setVisibility(View.GONE);
        if (shortForm) trialInputGroup.setVisibility(View.GONE);
    }

    // Toggles between Calendar and TextEdit inputs
    private void toggleCalendar() {
        showCalendar = !showCalendar;
        if (showCalendar){
            calendar.setVisibility(View.VISIBLE);
            allInputsGroup.setVisibility(View.GONE);

            /**
             * Stack Overflow
             * author: Reto Meier - https://stackoverflow.com/users/822
             * title: How do you close/hide the Android soft keyboard using Java?
             * date: October 28, 2020
             * link: https://stackoverflow.com/a/1109108
             * license: CC BY-SA 4.0
             */
            inputManager.hideSoftInputFromWindow(calendar.getWindowToken(), 0);
        }
        else
        {
            calendar.setVisibility(View.GONE);
            allInputsGroup.setVisibility(View.VISIBLE);
            if (shortForm) trialInputGroup.setVisibility(View.GONE);
        }
    }


    // validates input for all EditText fields, returns false if invalid input, displays warning if showError = true
    private boolean checkInputsValid(boolean showError) {
        return  Validate.lengthInRange(descriptionEditText, 1, 40, showError) &&
                Validate.intInRange(successCountEditText, 0, Integer.MAX_VALUE, showError) &&
                Validate.intInRange(failCountEditText, 0, Integer.MAX_VALUE, showError);
    }

}
