package com.killins.fitnesstracker.ui.workout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.killins.fitnesstracker.R;

public class WeightsActivity extends AppCompatActivity {

    int sets = 3, reps = 10, setsRemain, timeRemain, setStartTime;
    boolean blinkRed = false;
    int blinkCount = 6;
    String defaultTextColor;
    TextView timerText;

    WorkoutViewModel workoutViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weights);

        workoutViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);

        timerText = findViewById(R.id.timerText);
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);

        TextView setsNumView = findViewById(R.id.numberSetText);
        TextView repsNumView = findViewById(R.id.numberRepText);
        setsNumView.setText(String.valueOf(sets));
        repsNumView.setText(String.valueOf(reps));

        defaultTextColor = String.format("#%06X", (0xFFFFFF & timerText.getCurrentTextColor()));


        findViewById(R.id.increaseSetButton).setOnClickListener(v -> {
            sets++;
            setsNumView.setText(String.valueOf(sets));
        });

        findViewById(R.id.decreaseSetButton).setOnClickListener(v -> {
            sets--;
            setsNumView.setText(String.valueOf(sets));
        });

        findViewById(R.id.increaseRepButton).setOnClickListener(v -> {
            reps++;
            repsNumView.setText(String.valueOf(reps));
        });

        findViewById(R.id.decreaseRepButton).setOnClickListener(v -> {
            reps--;
            repsNumView.setText(String.valueOf(reps));
        });

        startButton.setOnClickListener(v -> {
            //start button
            timeRemain = reps * 4;
            setStartTime = timeRemain;
            timerText.setText(String.valueOf(timeRemain));
            setsRemain = sets;
            handler.postDelayed(setTimer, 1000);
        });

        stopButton.setOnClickListener(v -> {
            stopWorkout();
        });
    }

    private void stopWorkout(){
        //**** INSERT DATA TO DB ****//
        int weight = Integer.parseInt(((EditText)findViewById(R.id.weightEditText)).getText().toString());

        workoutViewModel.insertWeightWorkout(sets, reps, weight);

        handler.removeCallbacks(setTimer);
        handler.removeCallbacks(blinkTimerText);
        handler.removeCallbacks(breakTimer);
        finish();
    }

    Handler handler = new Handler();

    Runnable setTimer = new Runnable() {
        @Override
        public void run() {
            ((TextView)findViewById(R.id.timerTitle)).setText(R.string.time_left_in_set);
            timeRemain--;
            timerText.setText(String.valueOf(timeRemain));
            if(timeRemain > 0)
                handler.postDelayed(setTimer, 1000);
            else {
                handler.removeCallbacks(setTimer);
                setsRemain--;
                handler.postDelayed(blinkTimerText, 100);
            }
        }
    };

    Runnable blinkTimerText = new Runnable() {
        @Override
        public void run() {
            if(blinkRed){
                timerText.setBackgroundColor(getColor(R.color.red));
                timerText.setTextColor(getColor(R.color.white));
            } else {
                timerText.setTextColor(Color.parseColor(defaultTextColor));
                timerText.setBackgroundColor(getColor(R.color.transparent));
            }
            blinkRed = !blinkRed;
            if(blinkCount > 0) {
                blinkCount--;
                handler.postDelayed(blinkTimerText, 100);
            }
            else{
                handler.removeCallbacks(blinkTimerText);
                if(setsRemain > 0) {
                    timeRemain = setStartTime+1;
                    handler.postDelayed(breakTimer, 1000);
                }
                else stopWorkout();
            }
        }
    };

    Runnable breakTimer = new Runnable() {
        @Override
        public void run() {
            ((TextView)findViewById(R.id.timerTitle)).setText(R.string.time_left_in_break);
            timeRemain--;
            timerText.setText(String.valueOf(timeRemain));
            if(timeRemain > 0)
                handler.postDelayed(breakTimer, 1000);
            else {
                handler.removeCallbacks(breakTimer);
                timeRemain = setStartTime+1;
                handler.postDelayed(setTimer, 1000);
            }
        }
    };
}
