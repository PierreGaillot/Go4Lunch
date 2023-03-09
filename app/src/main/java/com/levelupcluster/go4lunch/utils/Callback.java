package com.levelupcluster.go4lunch.utils;

import com.levelupcluster.go4lunch.domain.models.Workmate;

import java.util.List;

public interface Callback<T> {
    void onCallback(T result);
}
