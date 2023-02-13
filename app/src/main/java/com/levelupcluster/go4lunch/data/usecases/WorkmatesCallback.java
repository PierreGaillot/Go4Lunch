package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.domain.models.Workmate;

import java.util.List;

public interface WorkmatesCallback {
    void onCallback(List<Workmate> workmateList);
}
