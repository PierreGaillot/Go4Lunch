package com.levelupcluster.go4lunch.ui.workmates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.data.usecases.WorkmatesCallback;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesViewModel extends ViewModel {

    private GetAllWorkmatesUseCase getAllWorkmatesUseCase = GetAllWorkmatesUseCase.instance;

    private final MutableLiveData<String> mText;

    public WorkmatesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Workmates fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void getWorkmates(GetWorkmateCallback getWorkmateCallback) throws FirebaseAuthException {
        List<Workmate> workmates = new ArrayList<>();
        getAllWorkmatesUseCase.getWorkmates(new WorkmatesCallback() {
            @Override
            public void onCallback(List<Workmate> workmateList) {
                System.out.println("DEBUG View Model");
                System.out.println(workmateList);
                getWorkmateCallback.onCallback(workmateList);
            }
        });
    }
}

interface GetWorkmateCallback{
    void onCallback(List<Workmate> workmateList);
}


