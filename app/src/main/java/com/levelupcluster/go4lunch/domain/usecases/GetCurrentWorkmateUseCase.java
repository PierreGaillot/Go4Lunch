package com.levelupcluster.go4lunch.domain.usecases;

import com.levelupcluster.go4lunch.data.usecases.GetCurrentWorkmateUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.utils.Callback;

public interface GetCurrentWorkmateUseCase {

    GetCurrentWorkmateUseCase instance = new GetCurrentWorkmateUseCaseImpl();

    void getCurrentWorkmate(Callback<Workmate> callback);
}
