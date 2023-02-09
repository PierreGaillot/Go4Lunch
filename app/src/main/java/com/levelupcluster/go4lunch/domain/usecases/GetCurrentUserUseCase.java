package com.levelupcluster.go4lunch.domain.usecases;

import com.levelupcluster.go4lunch.data.usecases.GetCurrentUserUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.User;

public interface GetCurrentUserUseCase {

    GetCurrentUserUseCase instance = new GetCurrentUserUseCaseImpl();

    User invoke();
}

