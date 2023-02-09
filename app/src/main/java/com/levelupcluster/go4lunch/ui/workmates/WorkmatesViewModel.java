package com.levelupcluster.go4lunch.ui.workmates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;

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

    public void getWorkmates() throws FirebaseAuthException {
        getAllWorkmatesUseCase.getWorkmates();
    }
}