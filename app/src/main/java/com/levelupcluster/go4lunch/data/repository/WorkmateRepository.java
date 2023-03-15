package com.levelupcluster.go4lunch.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.levelupcluster.go4lunch.utils.Callback;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.models.Workmate;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class WorkmateRepository {


    List<Workmate> workmates = new ArrayList<>();
    UserRepository userRepository = UserRepository.getInstance();
    private static volatile WorkmateRepository instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private WorkmateRepository() {
    }

    public static WorkmateRepository getInstance() {
        WorkmateRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (WorkmateRepository.class) {
            if (instance == null) {
                instance = new WorkmateRepository();
            }
            return instance;
        }
    }

    @Nullable
    public void getWorkmates(Callback<List<Workmate>> callback) {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Workmate> workmateList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Workmate workmate = new Workmate(document.getString("imageUrl"), document.getString("displayName"), document.getString("email"));

                        workmate.setRestaurantChoiceId(document.getString("restaurantChoiceId"));
                        workmateList.add(workmate);
                    }
                    workmates = workmateList;
                    callback.onCallback(workmates);
                }
            }
        });
    }

    public void getCurrentWorkmate(Callback<Workmate> callback) {
        User currentUser = userRepository.getCurrentUser();
        getWorkmates(new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> result) {
                for (Workmate wm : result) {
                    if (wm.getEmail().equals(currentUser.getEmail())) {
                        callback.onCallback(wm);
                    }
                }
            }
        });

    }

}
