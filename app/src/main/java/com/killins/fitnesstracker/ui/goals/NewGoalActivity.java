package com.killins.fitnesstracker.ui.goals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.killins.fitnesstracker.R;

public class NewGoalActivity extends AppCompatActivity {
    public static final String EXTRA_GOAL = "com.killins.fitnesstracker.GOAL";
    public static final String EXTRA_VALUE = "com.killins.fitnesstracker.VALUE";
    private EditText input_goal_name;
    private EditText input_goal_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        input_goal_name = findViewById(R.id.input_goal_name);
        input_goal_value = findViewById(R.id.input_goal_value);

        final Button addGoal = findViewById(R.id.addGoal);

        addGoal.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(input_goal_name.getText())||TextUtils.isEmpty(input_goal_value.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String goalName = input_goal_name.getText().toString();
                String goalValue = input_goal_value.getText().toString();
                replyIntent.putExtra(EXTRA_GOAL, goalName);
                replyIntent.putExtra(EXTRA_VALUE, goalValue);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}