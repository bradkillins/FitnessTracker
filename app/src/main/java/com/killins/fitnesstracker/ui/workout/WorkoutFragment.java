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
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.ui.goals.GoalsViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WorkoutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_workout, container, false);

        root.findViewById(R.id.start_run_button).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RunTracker.class);
            startActivity(intent);
        });

        root.findViewById(R.id.start_weight_button).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WeightsActivity.class);
            startActivity(intent);
        });

        root.findViewById(R.id.start_body_button).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BodyWeightActivity.class);
            startActivity(intent);
        });

        return root;
    }
}