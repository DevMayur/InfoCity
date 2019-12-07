package com.example.infocity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infocity.R;
import com.example.infocity.models.FeedbackModel;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private List<FeedbackModel> fList;
    private Context context;

    public FeedbackAdapter(List<FeedbackModel> fList, Context context) {
        this.fList = fList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_feedback_item, parent, false);
        return new FeedbackAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_email.setText(fList.get(position).getEmail());
        holder.tv_feedback.setText(fList.get(position).getFeedback());

    }

    @Override
    public int getItemCount() {
        return fList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_feedback,tv_email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_feedback = itemView.findViewById(R.id.tv_feedback_single_feedback);
            tv_email = itemView.findViewById(R.id.tv_email_single_feedback);

        }
    }
}
