package com.levelupcluster.go4lunch.domain.usecases;

import com.google.firebase.auth.FirebaseAuthException;
import com.levelupcluster.go4lunch.data.usecases.GetAllWorkmatesUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.Workmate;

import java.util.List;

public interface GetAllWorkmatesUseCase {

    GetAllWorkmatesUseCase instance = new GetAllWorkmatesUseCaseImpl();


    List<Workmate> getWorkmates() throws FirebaseAuthException;
}
