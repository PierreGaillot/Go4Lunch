package com.levelupcluster.go4lunch.ui.listView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListViewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is List View fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}