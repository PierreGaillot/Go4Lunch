package com.levelupcluster.go4lunch.domain.usecases;

import com.google.firebase.auth.FirebaseUser;
import com.levelupcluster.go4lunch.data.usecases.CreateUserUseCaseImpl;
import com.levelupcluster.go4lunch.data.usecases.GetCurrentUserUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.User;

public interface CreateUserUseCase {

    CreateUserUseCase instance = new CreateUserUseCaseImpl();


   public void createUser(User user);
}
