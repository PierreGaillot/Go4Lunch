package com.levelupcluster.go4lunch.ui.mapView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.levelupcluster.go4lunch.databinding.FragmentMapViewBinding;

public class MapViewFragment extends Fragment {

    private FragmentMapViewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewViewModel mapViewViewModel =
                new ViewModelProvider(this).get(MapViewViewModel.class);

        binding = FragmentMapViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        mapViewViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}