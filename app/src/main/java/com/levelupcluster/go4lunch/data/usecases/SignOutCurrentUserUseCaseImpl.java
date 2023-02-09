package com.levelupcluster.go4lunch.data.usecases;


import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.levelupcluster.go4lunch.data.repository.UserRepository;
import com.levelupcluster.go4lunch.domain.usecases.SignOutCurrentUserUseCase;

public class SignOutCurrentUserUseCaseImpl implements SignOutCurrentUserUseCase {

    private UserRepository userRepository = UserRepository.getInstance();

    @Override
    public void signOut(Context context) {
        userRepository.signOut(context);
    }
}
