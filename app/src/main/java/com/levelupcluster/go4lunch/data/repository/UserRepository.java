package com.levelupcluster.go4lunch.data.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.levelupcluster.go4lunch.domain.models.User;

public final class UserRepository {

    private static volatile UserRepository instance;

    private UserRepository() { }

    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserRepository.class) {
            if (instance == null) {
                instance = new UserRepository();
            }
            return instance;
        }
    }

    @Nullable
    public User getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return null;

        Uri photoUri = user.getPhotoUrl();
        String photoUrl = "https://www.pngitem.com/pimgs/m/150-1503945_transparent-user-png-default-user-image-png-png.png";
        if(photoUri != null) photoUrl = photoUri.toString();

        return new User(
                photoUrl,
                user.getDisplayName(),
                user.getEmail()
        );
    }

    public void createUser(){




    }

    public void signOut(Context context){
        AuthUI.getInstance().signOut(context);
    }
}