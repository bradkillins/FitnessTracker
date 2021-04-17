package com.killins.fitnesstracker.ui.stats;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.db.entities.Workout;

import java.util.List;

import static android.content.ContentValues.TAG;

public class WorkoutListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Workout> workouts; // Cached copy of workouts
    private static ClickListener clickListener;

    WorkoutListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case 0:
                View itemView = mInflater.inflate(R.layout.run_workout_item, parent, false);
                return new WorkoutRunViewHolder(itemView);
            case 1:
                View itemView1 = mInflater.inflate(R.layout.run_workout_item, parent, false);
                return new WorkoutBodyWeightViewHolder(itemView1);
            case 2:
                View itemView2 = mInflater.inflate(R.layout.run_workout_item, parent, false);
                return new WorkoutWeightsViewHolder(itemView2);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Workout currentWorkout = workouts.get(position);
        switch(holder.getItemViewType()){
            case 0:
                WorkoutRunViewHolder workoutRunViewHolder = (WorkoutRunViewHolder)holder;
                ((WorkoutRunViewHolder) holder).workoutLabel.setText(R.string.title_workout);
                ((WorkoutRunViewHolder) holder).distanceLabel.setText(R.string.distance_label);
                ((WorkoutRunViewHolder) holder).runDistance.setText(String.valueOf(currentWorkout.getDistance()));
                ((WorkoutRunViewHolder) holder).runTimeLabel.setText(R.string.runtime_label);
                ((WorkoutRunViewHolder) holder).runTime.setText(String.valueOf((int) currentWorkout.getDuration()));
                break;
            case 1:
                WorkoutBodyWeightViewHolder workoutBodyWeightViewHolder = (WorkoutBodyWeightViewHolder)holder;
                ((WorkoutBodyWeightViewHolder) holder).workoutLabel.setText(R.string.bodyweight_workout_title);
                ((WorkoutBodyWeightViewHolder) holder).bodyweightRepsLabel.setText(R.string.bodyweight_reps);
                ((WorkoutBodyWeightViewHolder) holder).workoutTime.setText(String.valueOf((int) currentWorkout.getDuration()));
                ((WorkoutBodyWeightViewHolder) holder).bodyweightSets.setText(R.string.weights_sets);
                ((WorkoutBodyWeightViewHolder) holder).bodyweight_num_sets.setText(String.valueOf(currentWorkout.getSets()));
                ((WorkoutBodyWeightViewHolder) holder).bodyweight_num_reps.setText(String.valueOf(currentWorkout.getSets()));
                ((WorkoutBodyWeightViewHolder) holder).bodyweightTimeLabel.setText(R.string.bodyweight_time_label);
                break;
            case 2:
                WorkoutWeightsViewHolder workoutWeightsViewHolder = (WorkoutWeightsViewHolder)holder;
                ((WorkoutWeightsViewHolder) holder).workoutLabel.setText(R.string.weights_workout_title);
                ((WorkoutWeightsViewHolder) holder).weightsReps.setText(R.string.weights_reps);
                ((WorkoutWeightsViewHolder) holder).workoutTime.setText(String.valueOf((int) currentWorkout.getDuration()));
                ((WorkoutWeightsViewHolder) holder).weightsSets.setText(R.string.weights_sets);
                ((WorkoutWeightsViewHolder) holder).num_sets.setText(String.valueOf(currentWorkout.getSets()));
                ((WorkoutWeightsViewHolder) holder).num_reps.setText(String.valueOf(currentWorkout.getSets()));
                ((WorkoutWeightsViewHolder) holder).bodyweightTimeLabel.setText(R.string.bodyweight_time_label);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        //Log.d(TAG, "getItemViewType: " + (workouts.get(position).workoutType == "runWorkout"));
        if (workouts.get(position).workoutType.equals("runWorkout") ){
            return 0;
        } else if (workouts.get(position).workoutType.equals("weightsWorkout")){
            return 1;
        }else if (workouts.get(position).workoutType.equals("body_weightWorkout") ){
            return 2;
        };
        return -1;
    }

    /**
     * Associates a list of Workouts with this adapter
     */
    void setWorkout(List<Workout> Workouts) {
        workouts = Workouts;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mWords has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (workouts != null)
            return workouts.size();
        else return 0;
    }

    /**
     * Gets the word at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the word in the RecyclerView
     * @return The word at the given position
     */
    public Workout getWorkoutAtPosition(int position) {
        return workouts.get(position);
    }

    class WorkoutRunViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutLabel;
        private final TextView distanceLabel;
        private final TextView runDistance;
        private final TextView runTimeLabel;
        private final TextView runTime;

        private WorkoutRunViewHolder(View itemView) {
            super(itemView);
            workoutLabel = itemView.findViewById(R.id.runWorkout);
            distanceLabel = itemView.findViewById(R.id.run_distance_label);
            runDistance = itemView.findViewById(R.id.run_distance);
            runTimeLabel = itemView.findViewById(R.id.textViewRun);
            runTime = itemView.findViewById(R.id.textViewRunTime);
            //Use on click listener to set up updating functionality
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    clickListener.onItemClick(view, getAdapterPosition());
//                }
//            });
        }
    }
    class WorkoutBodyWeightViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutLabel;
        private final TextView workoutTime;
        private final TextView bodyweightTimeLabel;
        private final TextView bodyweightSets;
        private final TextView bodyweight_num_sets;
        private final TextView bodyweightRepsLabel;
        private final TextView bodyweight_num_reps;

        private WorkoutBodyWeightViewHolder(View itemView) {
            super(itemView);
            workoutLabel = itemView.findViewById(R.id.bodyweightWorkout);
            bodyweightRepsLabel =itemView.findViewById(R.id.bodyweight_reps);
            workoutTime = itemView.findViewById(R.id.bodyWeightTime);
            bodyweightSets = itemView.findViewById(R.id.bodyweight_sets);
            bodyweight_num_sets = itemView.findViewById(R.id.bodyweight_num_sets);
            bodyweight_num_reps = itemView.findViewById(R.id.bodyweight_num_reps);
            bodyweightTimeLabel = itemView.findViewById(R.id.bodyWeight_time_label);
        }
    }
    class WorkoutWeightsViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutLabel;
        private final TextView workoutTime;
        private final TextView bodyweightTimeLabel;
        private final TextView weightsSets;
        private final TextView num_sets;
        private final TextView weightsReps;
        private final TextView num_reps;

        private WorkoutWeightsViewHolder(View itemView) {
            super(itemView);
            workoutLabel = itemView.findViewById(R.id.weightsWorkout);
            weightsReps =itemView.findViewById(R.id.weights_reps);
            workoutTime = itemView.findViewById(R.id.textViewWeightTime);
            weightsSets = itemView.findViewById(R.id.weights_sets);
            num_sets = itemView.findViewById(R.id.num_sets);
            num_reps = itemView.findViewById(R.id.num_reps);
            bodyweightTimeLabel = itemView.findViewById(R.id.bodyWeight_time_label);

        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        WorkoutListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
