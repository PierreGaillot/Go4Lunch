package com.levelupcluster.go4lunch.domain.usecases;

import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.data.usecases.GetAllWorkmatesUseCaseImpl;
import com.levelupcluster.go4lunch.data.usecases.WorkmatesCallback;

public interface GetAllWorkmatesUseCase {

    GetAllWorkmatesUseCase instance = new GetAllWorkmatesUseCaseImpl();

    void getWorkmates(WorkmatesCallback workmatesCallback) throws FirebaseAuthException;
}
