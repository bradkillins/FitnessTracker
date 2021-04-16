package com.killins.fitnesstracker.ui.workout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.killins.fitnesstracker.R;

public class RunStopDialog  extends DialogFragment {
    int runTimeMin;
    int runTimeSec;
    int runDistance;

    private RunStopDialogListener listener;

    public RunStopDialog(int runTimeMin, int runTimeSec, int runDistance){
        this.runTimeMin = runTimeMin;
        this.runTimeSec = runTimeSec;
        this.runDistance = runDistance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Great Work!")
                .setMessage(getString(R.string.run_stop_dialog_message, runDistance, runTimeMin, runTimeSec))
                .setPositiveButton("Okay", (dialog, which) -> listener.onRunStopDialogPositiveClick());
        return builder.create();
    }

    public interface RunStopDialogListener {
        void onRunStopDialogPositiveClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (RunStopDialog.RunStopDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement RunStopDialogListener.");
        }
    }
}
