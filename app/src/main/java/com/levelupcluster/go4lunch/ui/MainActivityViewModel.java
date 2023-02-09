package com.levelupcluster.go4lunch.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.usecases.GetCurrentUserUseCase;
import com.levelupcluster.go4lunch.domain.usecases.SignOutCurrentUserUseCase;
import com.levelupcluster.go4lunch.ui.uimodels.DrawerHeaderUiModel;

public class MainActivityViewModel extends ViewModel {

    private GetCurrentUserUseCase mGetCurrentUserUseCase = GetCurrentUserUseCase.instance;
    private SignOutCurrentUserUseCase signOutCurrentUserUseCase = SignOutCurrentUserUseCase.instance;

    private final MutableLiveData<DrawerHeaderUiModel> headerData = new MutableLiveData<>();
    public LiveData<DrawerHeaderUiModel> getHeaderData() {
        return headerData;
    }

    void reloadCurrentUser(){
        User currentUser =  mGetCurrentUserUseCase.invoke();

        if(currentUser == null) {
            headerData.postValue(null);
        } else {
            headerData.postValue(new DrawerHeaderUiModel(
                    currentUser.getImageURL(),
                    currentUser.getDisplayName(),
                    currentUser.getEmail()
            ));
        }
    }

    void signOutUser(Context context){
        signOutCurrentUserUseCase.signOut(context);
    }

}