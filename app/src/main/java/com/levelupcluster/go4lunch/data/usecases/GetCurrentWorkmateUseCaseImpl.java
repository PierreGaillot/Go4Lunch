package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.WorkmateRepository;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetCurrentWorkmateUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

public class GetCurrentWorkmateUseCaseImpl implements GetCurrentWorkmateUseCase {

    WorkmateRepository workmateRepository = WorkmateRepository.getInstance();


    @Override
    public void getCurrentWorkmate(Callback<Workmate> callback) {
        workmateRepository.getCurrentWorkmate(callback);
    }
}
