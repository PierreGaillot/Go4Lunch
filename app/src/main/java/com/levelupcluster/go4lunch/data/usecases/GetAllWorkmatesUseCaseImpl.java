package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.WorkmateRepository;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.List;

public class GetAllWorkmatesUseCaseImpl implements GetAllWorkmatesUseCase {

    WorkmateRepository workmateRepository = WorkmateRepository.getInstance();

    @Override
    public void getWorkmates(Callback<List<Workmate>> callback) {
        workmateRepository.getWorkmates(callback);
    }
}


