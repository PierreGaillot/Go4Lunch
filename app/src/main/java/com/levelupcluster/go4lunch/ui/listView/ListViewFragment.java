package com.levelupcluster.go4lunch.ui.listView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.levelupcluster.go4lunch.R;
import com.levelupcluster.go4lunch.databinding.FragmentListViewBinding;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.ui.MainActivity;
import com.levelupcluster.go4lunch.ui.RestaurantViewModel;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment {
    private FragmentListViewBinding binding;
    RecyclerView recyclerView;
    ListViewAdapter adapter;
    RestaurantViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        binding = FragmentListViewBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        recyclerView =  view.findViewById(R.id.restaurants_listView_recyclerView);

        initList();

        return view;
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListViewAdapter(new ArrayList<>(), new ListViewAdapterItemClickListener() {
            @Override
            public void onItemClick(String restaurantId) {
                viewModel.getRestaurantDetails(restaurantId, new Callback<RestaurantDetails>() {
                    @Override
                    public void onCallback(RestaurantDetails result) {
                        ((MainActivity)getActivity()).openRestaurantViewFragment(result);
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
        refreshList();
    }

    private void refreshList() {
        viewModel.restaurants.observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                adapter.update(restaurants);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}