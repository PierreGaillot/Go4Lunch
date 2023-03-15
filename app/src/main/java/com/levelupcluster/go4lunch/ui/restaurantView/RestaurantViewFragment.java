package com.levelupcluster.go4lunch.ui.restaurantView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.R;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.ui.MainActivityViewModel;
import com.levelupcluster.go4lunch.ui.workmates.WorkmatesAdapter;
import com.levelupcluster.go4lunch.utils.Callback;

import org.checkerframework.checker.units.qual.C;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RestaurantViewFragment extends Fragment {

    private static final String argumentKey = "restaurant";

    TextView displayName, info;
    LinearLayout callLL, likeLL, websiteLL;
    ImageView headerImage;
    RatingBar ratingBar;
    FloatingActionButton fab;
    RestaurantDetails restaurant;
    private RestaurantViewAdapter adapter;
    private RecyclerView recyclerView;
    private RestaurantViewViewModel viewModel;

    public static RestaurantViewFragment newInstance(RestaurantDetails restaurantDetailsModel) {
        RestaurantViewFragment fragment = new RestaurantViewFragment();
        Bundle restaurantBundle = new Bundle();
        restaurantBundle.putSerializable(argumentKey, restaurantDetailsModel);
        fragment.setArguments(restaurantBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantViewViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            restaurant = (RestaurantDetails) getArguments().getSerializable(argumentKey);
        }

        viewModel.getCurrentWorkmate(new Callback<Workmate>() {
            @Override
            public void onCallback(Workmate result) {
                if (restaurant.getId().equals(result.getRestaurantChoiceId())) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.success)));
                }
            }
        });
        bindView();

        Glide.with(view.getContext())
                .load(restaurant.getPhotoUrl())
                .centerCrop()
                .into(headerImage);

        viewModel.getWorkmateByRestaurant(restaurant.getId(), new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> result) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RestaurantViewAdapter(new ArrayList<>());
                recyclerView.setAdapter(adapter);
                refreshList();
            }
        });
    }

    private void refreshList() {
        viewModel.workmates.observe(getViewLifecycleOwner(), new Observer<List<Workmate>>() {
            @Override
            public void onChanged(List<Workmate> workmates) {
                adapter.update(workmates);
            }
        });
    }

    private void bindView() {
        displayName.setText(restaurant.getName());
        info.setText(restaurant.getVicinity());
        ratingBar.setRating(Float.parseFloat(restaurant.getRating()));

        if (restaurant.getWebsite() != null) {
            websiteLL.setVisibility(View.VISIBLE);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
            websiteLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(browserIntent);
                }
            });
        }
        if (restaurant.getFormattedPhoneNumber() != null) {
            callLL.setVisibility(View.VISIBLE);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + restaurant.getFormattedPhoneNumber()));
            callLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(callIntent);
                }
            });
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getCurrentWorkmate(new Callback<Workmate>() {
                    @Override
                    public void onCallback(Workmate workmate) {
                        if (restaurant.getId().equals(workmate.getRestaurantChoiceId())) {
                            Toast.makeText(getContext(), getContext().getString(R.string.you_already_eat_at) + restaurant.getName(), Toast.LENGTH_SHORT).show();
                        } else {
                            viewModel.updateRestaurantChoice(restaurant.getId(), new Callback<Void>() {
                                @Override
                                public void onCallback(Void result) {
                                    Toast.makeText(getContext(), getContext().getString(R.string.you_now_eat_at) + restaurant.getName(), Toast.LENGTH_SHORT).show();
                                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.success)));
                                    viewModel.getWorkmateByRestaurant(restaurant.getId(), new Callback<List<Workmate>>() {
                                        @Override
                                        public void onCallback(List<Workmate> result) {
                                            refreshList();
                                        }
                                    });
                                }
                            });

                        }
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_view, container, false);
        recyclerView = view.findViewById(R.id.restView_recyclerView_workmates);
        displayName = view.findViewById(R.id.restView_TextView_displayName);
        info = view.findViewById(R.id.restView_TextView_vicinity);
        ratingBar = view.findViewById(R.id.restView_ratingBar);
        callLL = view.findViewById(R.id.restView_iconLL_call);
        likeLL = view.findViewById(R.id.restView_iconLL_like);
        websiteLL = view.findViewById(R.id.restView_iconLL_website);
        fab = view.findViewById(R.id.restView_fab);
        headerImage = view.findViewById(R.id.restView_imageView_header);

        callLL.setVisibility(View.GONE);
        websiteLL.setVisibility(View.GONE);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        viewModel.workmates.observe(getViewLifecycleOwner(), new Observer<List<Workmate>>() {
            @Override
            public void onChanged(List<Workmate> workmates) {
                initList();
            }
        });


        return view;
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantViewAdapter(new ArrayList<Workmate>() {
        });
        recyclerView.setAdapter(adapter);
        refreshList();
    }

}