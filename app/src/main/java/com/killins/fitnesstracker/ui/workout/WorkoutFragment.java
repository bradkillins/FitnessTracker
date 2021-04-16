package com.killins.fitnesstracker.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.RunTracker;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.ui.goals.GoalsViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel workoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        workoutViewModel =
                new ViewModelProvider(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        workoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        root.findViewById(R.id.startRunTest).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RunTracker.class);
            startActivity(intent);
        });

        return root;
    }
}