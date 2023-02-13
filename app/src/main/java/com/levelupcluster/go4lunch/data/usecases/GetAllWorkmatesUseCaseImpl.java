package com.levelupcluster.go4lunch.data.usecases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetCurrentUserUseCase;

import java.util.ArrayList;
import java.util.List;

public class GetAllWorkmatesUseCaseImpl implements GetAllWorkmatesUseCase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getWorkmates(WorkmatesCallback workmatesCallback) {

        User currentUser = GetCurrentUserUseCase.instance.invoke();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Workmate> workmateList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Workmate workmate = new Workmate(document.getString("displayName"), document.getString("imageUrl"), document.getString("email") );
                        if (!currentUser.getEmail().equals(workmate.getEmail())) workmateList.add(workmate);
                    }
                    workmatesCallback.onCallback(workmateList);
                }
            }
        });
    }
}


