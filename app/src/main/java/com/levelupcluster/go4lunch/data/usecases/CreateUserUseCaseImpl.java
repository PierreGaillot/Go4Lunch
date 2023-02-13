package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.UserRepository;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.usecases.CreateUserUseCase;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository = UserRepository.getInstance();


    @Override
    public void createUser(User user) {
        userRepository.createUser(user);
    }
}
