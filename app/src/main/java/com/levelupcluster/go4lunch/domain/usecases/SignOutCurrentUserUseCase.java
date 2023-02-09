package com.levelupcluster.go4lunch.domain.usecases;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.levelupcluster.go4lunch.data.usecases.SignOutCurrentUserUseCaseImpl;

public interface SignOutCurrentUserUseCase {

    SignOutCurrentUserUseCase instance = new SignOutCurrentUserUseCaseImpl();

    void signOut(Context context);
}
