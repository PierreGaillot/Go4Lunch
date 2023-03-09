package com.levelupcluster.go4lunch.ui.restaurantView;

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
import com.levelupcluster.go4lunch.ui.listView.ListViewAdapter;

import java.util.List;

public class RestaurantViewAdapter extends RecyclerView.Adapter<RestaurantViewAdapter.RestaurantViewViewHolder> {

    List<Workmate> workmates;

    RestaurantViewAdapter(List<Workmate> workmates){
        this.workmates = workmates;
    }

    public static class RestaurantViewViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        public RestaurantViewViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.workmate_item_row_textView);
            image = itemView.findViewById(R.id.workmate_item_row_profile_imageView);
        }
    }


    @NonNull
    @Override
    public RestaurantViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.workmate_item_row, parent, false);
        return new RestaurantViewAdapter.RestaurantViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewViewHolder holder, int position) {
        holder.text.setText(workmates.get(position).getDisplayName());
        Glide.with(holder.itemView.getContext())
                .load(workmates.get(position).getImageURL())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return workmates.size();
    }

    public void update(List<Workmate> list) {
        workmates.clear();
        workmates.addAll(list);
        notifyDataSetChanged();
    }

}
