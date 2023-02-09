package com.levelupcluster.go4lunch.data.repository;

import com.levelupcluster.go4lunch.domain.models.Workmate;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WorkmateRepository {

    private static volatile WorkmateRepository instance;

    private WorkmateRepository(){}

    public static WorkmateRepository getInstance() {
        WorkmateRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(WorkmateRepository.class) {
            if (instance == null) {
                instance = new WorkmateRepository();
            }
            return instance;
        }
    }

    @Nullable
    public List<Workmate> getWorkmates(){
        List<Workmate> workmates = new ArrayList<>();




        return workmates;
    }

}
