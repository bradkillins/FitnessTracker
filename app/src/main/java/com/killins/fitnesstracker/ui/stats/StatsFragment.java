package com.killins.fitnesstracker.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.entities.Workout;
import com.killins.fitnesstracker.ui.goals.GoalListAdapter;
import com.killins.fitnesstracker.ui.goals.GoalsViewModel;
import com.killins.fitnesstracker.ui.workout.WorkoutViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class StatsFragment extends Fragment {

    public String currentUserId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        currentUserId = requireActivity().getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");
        // Set up the RecyclerView.
        RecyclerView recyclerView = root.findViewById(R.id.statsRecyclerView);
        final WorkoutListAdapter adapter = new WorkoutListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        WorkoutViewModel workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        // Update the cached copy of the goals in the adapter.
        workoutViewModel.loadUserWorkouts(currentUserId).observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(@Nullable List<Workout> workouts) {
                adapter.setWorkout(workouts);
            }
        });
        return root;
    }
}