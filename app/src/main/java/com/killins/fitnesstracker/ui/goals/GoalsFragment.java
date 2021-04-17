package com.killins.fitnesstracker.ui.goals;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.killins.fitnesstracker.MainActivity;
import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.ui.login.LoginViewModel;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class GoalsFragment extends Fragment {
    public static final int NEW_GOAL_ACTIVITY_REQUEST_CODE = 1;
    public String currentUserId;
    private GoalsViewModel goalsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_goals, container, false);
        currentUserId = requireActivity().getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");

        // Set up the RecyclerView.
        RecyclerView recyclerView = root.findViewById(R.id.goalsRecyclerView);
        final GoalListAdapter adapter = new GoalListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        goalsViewModel = ViewModelProviders.of(this).get(GoalsViewModel.class);
        // Update the cached copy of the goals in the adapter.
        goalsViewModel.loadUserGoals(currentUserId).observe(getViewLifecycleOwner(), new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable List<Goal> goals) {
                adapter.setGoals(goals);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Floating action button setup
        FloatingActionButton fab = requireView().findViewById(R.id.fab);
        fab.setOnClickListener(currentView -> {
            Intent intent = new Intent(getActivity(), NewGoalActivity.class);
            startActivityForResult(intent, NEW_GOAL_ACTIVITY_REQUEST_CODE);
        });
    }



    /**
     * When the user enters a new goal in the NewGoalActivity,
     * that activity returns the result to this activity.
     * If the user entered a new goal, save it in the database.

     * @param requestCode ID for the request
     * @param resultCode indicates success or failure
     * @param data The Intent sent back from the NewGoalActivity,
     *             which includes the goal that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String goalName;
        String goalValue;
        if (requestCode == NEW_GOAL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            goalName = data.getStringExtra(NewGoalActivity.EXTRA_GOAL);
            goalValue = data.getStringExtra(NewGoalActivity.EXTRA_VALUE);
            Goal goal = new Goal(currentUserId, goalName, goalValue);
            goalsViewModel.insert(goal);
        } else {
            Toast.makeText(
                    getActivity(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}