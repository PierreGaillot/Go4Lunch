package com.levelupcluster.go4lunch.ui.restaurantView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levelupcluster.go4lunch.domain.usecases.GetCurrentWorkmateUseCase;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetWorkmateByRestaurantUseCase;
import com.levelupcluster.go4lunch.domain.usecases.UpdateRestaurantChoiceIdUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViewViewModel extends ViewModel {

    private final UpdateRestaurantChoiceIdUseCase updateRestaurantChoiceIdUseCase =UpdateRestaurantChoiceIdUseCase.instance;
    private final GetWorkmateByRestaurantUseCase getWorkmateByRestaurantUseCase = GetWorkmateByRestaurantUseCase.instance;
    private final GetCurrentWorkmateUseCase getCurrentWorkmateUseCase = GetCurrentWorkmateUseCase.instance;

    MutableLiveData<List<Workmate>> _workmates = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Workmate>> workmates = _workmates;

    public void updateRestaurantChoice(String restaurantId, Callback<Void> callback){
        updateRestaurantChoiceIdUseCase.updateRestaurantChoice(restaurantId, callback);
    }

    public void getWorkmateByRestaurant(String restaurantId, Callback<List<Workmate>> callback){
        getWorkmateByRestaurantUseCase.getWorkmateByRestaurant(restaurantId, new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> result) {
                _workmates.postValue(result);
                callback.onCallback(result);
            }
        });
    }

    public void getCurrentWorkmate(Callback<Workmate> callback){
        getCurrentWorkmateUseCase.getCurrentWorkmate(callback);
    }

}
