package com.levelupcluster.go4lunch.ui.workmates;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.levelupcluster.go4lunch.R;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.models.Workmate;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.MyViewHolder> {

    List<Workmate> workmates;

    public WorkmatesAdapter (List<Workmate> workmates) {
        this.workmates = workmates;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.workmate_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Workmate currentWm = workmates.get(position);
        String wmChoiceId = currentWm.getRestaurantChoiceId();
        Restaurant wmChoice = null;

        String workmateStatus = "";
        if (wmChoice != null) { // the workmate have a restaurant assign.
            workmateStatus = currentWm.getDisplayName() + " " + context.getString(R.string.is_eating) + " " + wmChoice.getCookStyle() + " (" + wmChoice.getName() + ")";
            holder.status.setTypeface(null, Typeface.BOLD);
            holder.status.setTextColor(context.getResources().getColor(com.firebase.ui.auth.R.color.design_default_color_on_background));
        } else { // the workmate does not have a restaurant assigned
            workmateStatus = currentWm.getDisplayName() + " " + context.getString(R.string.not_decided);
            holder.status.setTypeface(null, Typeface.ITALIC);
        }


        holder.status.setText(workmateStatus);
        Glide.with(context)
                .load(workmates.get(position).getImageURL())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return workmates.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.workmate_item_row_profile_imageView);
            status = itemView.findViewById(R.id.workmate_item_row_textView);
        }
    }

    public void update(List<Workmate> list) {
        workmates.clear();
        workmates.addAll(list);
        notifyDataSetChanged();
    }
}
