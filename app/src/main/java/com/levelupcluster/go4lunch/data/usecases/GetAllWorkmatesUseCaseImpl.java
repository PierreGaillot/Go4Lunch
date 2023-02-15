package com.levelupcluster.go4lunch.data.usecases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.levelupcluster.go4lunch.data.repository.WorkmateRepository;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetCurrentUserUseCase;

import java.util.ArrayList;
import java.util.List;

public class GetAllWorkmatesUseCaseImpl implements GetAllWorkmatesUseCase {

    WorkmateRepository workmateRepository = WorkmateRepository.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getWorkmates(WorkmatesCallback workmatesCallback) {
        workmateRepository.getWorkmates(workmatesCallback);
    }
}


