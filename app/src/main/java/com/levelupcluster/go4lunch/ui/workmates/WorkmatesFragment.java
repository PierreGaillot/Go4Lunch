package com.levelupcluster.go4lunch.ui.workmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.R;
import com.levelupcluster.go4lunch.databinding.FragmentWorkmatesBinding;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.ui.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesFragment extends Fragment {

    private FragmentWorkmatesBinding binding;
    private WorkmatesViewModel workmatesViewModel;

    private RestaurantViewModel restaurantViewModel;
    private WorkmatesAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkmatesViewModel workmatesViewModel =
                new ViewModelProvider(this).get(WorkmatesViewModel.class);
        RestaurantViewModel restaurantViewModel =
                new ViewModelProvider(this).get(RestaurantViewModel.class);


        binding = FragmentWorkmatesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.workmates_recyclerView);
        try {
            initList();
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    private void initList() throws FirebaseAuthException {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WorkmatesAdapter(new ArrayList<Workmate>());
        recyclerView.setAdapter(adapter);
        refreshList();
    }

    private void refreshList() throws FirebaseAuthException {
        workmatesViewModel.refreshWorkmates();
        workmatesViewModel.workmates.observe(getViewLifecycleOwner(), new Observer<List<Workmate>>() {
            @Override
            public void onChanged(List<Workmate> workmates) {
                adapter.update(workmates);
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workmatesViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}