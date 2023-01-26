package com.levelupcluster.go4lunch.ui.mapView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapViewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Map View fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}