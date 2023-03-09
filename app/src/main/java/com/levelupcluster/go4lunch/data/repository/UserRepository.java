package com.levelupcluster.go4lunch.data.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.levelupcluster.go4lunch.domain.models.User;

import java.util.HashMap;
import java.util.Map;

public final class UserRepository {

    private static final String TAG = "UserRepository";
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

    public void createUser(User u){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
//        user.put("uid", u.getUid());
        user.put("displayName", u.getDisplayName());
        user.put("email", u.getEmail());
        user.put("imageUrl", u.getImageURL());

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void signOut(Context context){
        AuthUI.getInstance().signOut(context);
    }

    public void updateRestaurantChoiceId(String restaurantId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User currentUser = getCurrentUser();
        db.collection("users").whereEqualTo("email", currentUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String userId = queryDocumentSnapshots.getDocuments().get(0).getId();
                db.collection("users").document(userId).update("restaurantChoiceId", restaurantId).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Vous vez choisi le restaurant : " + restaurantId);
                    }
                });
            }
        });

    }
}