package com.levelupcluster.go4lunch.ui.listView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.levelupcluster.go4lunch.R;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.ui.RestaurantViewModel;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.RestaurantsListViewHolder> {

    List<Restaurant> restaurants;
    ListViewAdapterItemClickListener listViewAdapterItemClickListener;

    RestaurantViewModel viewModel;

    public ListViewAdapter (List<Restaurant> restaurants, ListViewAdapterItemClickListener listViewAdapterItemClickListener) {
        this.restaurants = restaurants;
        this.listViewAdapterItemClickListener = listViewAdapterItemClickListener;
    }

    @NonNull
    @Override
    public RestaurantsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.restaurant_item_row, parent, false);
        return new RestaurantsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsListViewHolder holder, int position) {
        Restaurant currentRestaurant = restaurants.get(position);
        if (!currentRestaurant.getRating().equals("null")){
            holder.ratingBar.setRating((float) Float.parseFloat(currentRestaurant.getRating()));
        } else {
            holder.ratingBar.setRating((float) 0);
        }

        holder.displayName.setText(currentRestaurant.getName());
        holder.info.setText(currentRestaurant.getVicinity());
        holder.status.setText(currentRestaurant.getBusiness_status());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewAdapterItemClickListener.onItemClick(currentRestaurant.getId());
            }
        });

        Glide.with(holder.itemView.getContext())
                .load(currentRestaurant.getPhotoUrl())
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RestaurantsListViewHolder extends RecyclerView.ViewHolder {
        TextView displayName, info, status;
        RatingBar ratingBar;

        ImageView image;
        public RestaurantsListViewHolder(@NonNull View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.restaurant_item_row_displayName_textView);
            info = itemView.findViewById(R.id.restaurant_item_row_info_textView);
            status = itemView.findViewById(R.id.restaurant_item_row_status_textView);
            ratingBar = itemView.findViewById(R.id.restaurant_item_row_ratingBar);
            image = itemView.findViewById(R.id.restaurant_item_row_imageView);
        }
    }

    public void update(List<Restaurant> restaurants) {
        this.restaurants.clear();
        this.restaurants.addAll(restaurants);
        notifyDataSetChanged();
    }

}
