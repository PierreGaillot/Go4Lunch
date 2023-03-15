package com.levelupcluster.go4lunch.ui;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.CreateUserUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetCurrentUserUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetRestaurantDetailsUseCase;
import com.levelupcluster.go4lunch.domain.usecases.SignOutCurrentUserUseCase;
import com.levelupcluster.go4lunch.ui.uimodels.DrawerHeaderUiModel;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "MainActivityViewModel";
    private GetCurrentUserUseCase mGetCurrentUserUseCase = GetCurrentUserUseCase.instance;
    private SignOutCurrentUserUseCase signOutCurrentUserUseCase = SignOutCurrentUserUseCase.instance;
    private CreateUserUseCase createUserUseCase = CreateUserUseCase.instance;

    private GetAllWorkmatesUseCase getAllWorkmatesUseCase = GetAllWorkmatesUseCase.instance;

    private GetRestaurantDetailsUseCase getRestaurantDetailsUseCase = GetRestaurantDetailsUseCase.instance;

    private final MutableLiveData<DrawerHeaderUiModel> headerData = new MutableLiveData<>();

    public LiveData<DrawerHeaderUiModel> getHeaderData() {
        return headerData;
    }

    void reloadCurrentUser() {
        User currentUser = mGetCurrentUserUseCase.invoke();

        if (currentUser == null) {
            headerData.postValue(null);
        } else {
            headerData.postValue(new DrawerHeaderUiModel(
                    currentUser.getImageURL(),
                    currentUser.getDisplayName(),
                    currentUser.getEmail()
            ));
        }
    }

    void createUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User currentUser = mGetCurrentUserUseCase.invoke();
        List<User> users = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = new User(document.getString("imageUrl"), document.getString("displayName"), document.getString("email"));
                                users.add(user);
                            }
                            boolean userExist = false;
                            for (int i = 0; i < users.size(); i++) {
                                if (users.get(i).getEmail().equals(currentUser.getEmail()))
                                    userExist = true;
                            }
                            if (!userExist) createUserUseCase.createUser(currentUser);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    void signOutUser(Context context) {
        signOutCurrentUserUseCase.signOut(context);
    }

    void getUserRestaurantDetails(Callback<RestaurantDetails> callback) throws FirebaseAuthException {
        User currentUser = mGetCurrentUserUseCase.invoke();
        getAllWorkmatesUseCase.getWorkmates(new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> result) {
                try {
                    for (Workmate wm : result) {
                        if (currentUser.getEmail().equals(wm.getEmail())) {
                            if (wm.getRestaurantChoiceId() != null) {
                                getRestaurantDetailsUseCase.getRestaurantDetails(wm.getRestaurantChoiceId(), new Callback<RestaurantDetails>() {
                                    @Override
                                    public void onCallback(RestaurantDetails result) {
                                        callback.onCallback(result);
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

}