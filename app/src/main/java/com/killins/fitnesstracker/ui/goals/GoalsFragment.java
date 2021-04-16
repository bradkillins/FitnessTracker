package com.killins.fitnesstracker.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.db.entities.Goal;

import java.util.List;

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;

    public GoalsFragment() {
        super(R.layout.fragment_goals);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel = ViewModelProviders.of(this).get(GoalsViewModel.class);

        goalsViewModel.getUserGoals().observe(getViewLifecycleOwner(), goals -> {
            Toast.makeText(getContext(), "woot", Toast.LENGTH_SHORT).show();
        });

        View root = inflater.inflate(R.layout.fragment_goals, container, false);

        return root;
    }
}