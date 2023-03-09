package com.levelupcluster.go4lunch.ui.workmates;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.places.Place;
import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.utils.Callback;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkmatesViewModel extends ViewModel {

    private final GetAllWorkmatesUseCase getAllWorkmatesUseCase = GetAllWorkmatesUseCase.instance;

    private final MutableLiveData<List<Workmate>> _workmates = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Workmate>> workmates = _workmates;



    public void refreshWorkmates() throws FirebaseAuthException {
        getAllWorkmatesUseCase.getWorkmates(new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> workmateList) {
                _workmates.postValue(workmateList);
            }
        });
    }
}


