package com.levelupcluster.go4lunch.ui.workmates;

import android.content.Context;
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
import com.levelupcluster.go4lunch.domain.models.Workmate;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.MyViewHolder>{

    List<Workmate> workmates;
    Context context;


    public WorkmatesAdapter(Context context, List<Workmate> workmates) {
        this.context = context;
        this.workmates = workmates;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.workmate_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.status.setText(workmates.get(position).getDisplayName());
        Glide.with(context)
                .load(workmates.get(position).getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return workmates.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.workmate_item_row_profile_imageView);
            status = itemView.findViewById(R.id.workmate_item_row_textView);

        }
    }

    public void update(ArrayList<Workmate> list) {
        workmates.clear();
        workmates.addAll(list);
        notifyDataSetChanged();
        System.out.println(list);
    }
}
