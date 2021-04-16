package com.killins.fitnesstracker.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.killins.fitnesstracker.R;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel workoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workoutViewModel =
                new ViewModelProvider(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_workout, container, false);

        root.findViewById(R.id.start_run_button).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RunTracker.class);
            startActivity(intent);
        });

        return root;
    }
}