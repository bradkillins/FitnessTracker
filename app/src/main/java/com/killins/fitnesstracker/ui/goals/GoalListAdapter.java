package com.killins.fitnesstracker.ui.goals;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.db.entities.Goal;

import java.util.List;

/**
 * Adapter for the RecyclerView that displays a list of goals.
 */

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.GoalViewHolder> {

    private final LayoutInflater mInflater;
    private List<Goal> goals; // Cached copy of words
    private static ClickListener clickListener;

    GoalListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.goal_recyclerview_item, parent, false);
        return new GoalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {
        if (goals != null) {
            Goal current = goals.get(position);
            holder.goalLabelTextView.setText(current.getGoalName());
            holder.goalValueTextView.setText(current.getGoalValue());
        } else {
            // Covers the case of data not being ready yet.
            holder.goalLabelTextView.setText(R.string.no_goal);
            holder.goalValueTextView.setText(R.string.addValue);
        }
    }

    /**
     * Associates a list of goals with this adapter
     */
    void setGoals(List<Goal> Goals) {
        goals = Goals;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mWords has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (goals != null)
            return goals.size();
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
    public Goal getGoalAtPosition(int position) {
        return goals.get(position);
    }

    class GoalViewHolder extends RecyclerView.ViewHolder {
        private final TextView goalLabelTextView;
        private final TextView goalValueTextView;

        private GoalViewHolder(View itemView) {
            super(itemView);
            goalLabelTextView = itemView.findViewById(R.id.goalLabelTextView);
            goalValueTextView = itemView.findViewById(R.id.goalValueTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        GoalListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
