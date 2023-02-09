package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.UserRepository;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.usecases.GetCurrentUserUseCase;

public class GetCurrentUserUseCaseImpl implements GetCurrentUserUseCase {

    private UserRepository userRepository = UserRepository.getInstance();

    @Override
    public User invoke() {
        return userRepository.getCurrentUser();
    }
}
