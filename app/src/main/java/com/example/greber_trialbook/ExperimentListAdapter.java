package com.example.greber_trialbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Handles displaying the list of experiments from DataManager.
 * OnItemClickListener implemented for callback to MainActivity.
 */
public class ExperimentListAdapter extends RecyclerView.Adapter<ExperimentListAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, descriptionTextView, successRateTextView, totalTrialsTextView;
        public View container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            titleTextView = itemView.findViewById(R.id.experiment_description);
            descriptionTextView = itemView.findViewById(R.id.experiment_date);
            successRateTextView = itemView.findViewById(R.id.success_rate);
            totalTrialsTextView = itemView.findViewById(R.id.total_trials);
        }
    }


    public ExperimentListAdapter(ExperimentListAdapter.OnItemClickListener onItemClick) {
        setOnItemClickListener(onItemClick);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View experimentView = inflater.inflate(R.layout.experiment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(experimentView);
        if (listener != null) viewHolder.container.setOnClickListener(v -> listener.onItemClick(viewHolder.getAdapterPosition()));

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ExperimentListAdapter.ViewHolder holder, int position) {
        Experiment experiment = DataManager.getExperiment(position);
        holder.descriptionTextView.setText(experiment.getDateString());
        holder.titleTextView.setText(experiment.getDescription());
        holder.successRateTextView.setText(String.format("Success Rate: %.1f%%", experiment.getSuccessRate()));
        holder.totalTrialsTextView.setText(String.format("Trials: %d", experiment.getSuccesses() + experiment.getFailures()));
    }

    @Override
    public int getItemCount() {
        return DataManager.getExperiments().size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
