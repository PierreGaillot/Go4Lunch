package com.levelupcluster.go4lunch.ui.restaurantView;

import androidx.lifecycle.ViewModel;

import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetWorkmateByRestaurantUseCase;
import com.levelupcluster.go4lunch.domain.usecases.UpdateRestaurantChoiceIdUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.List;

public class RestaurantViewViewModel extends ViewModel {

    private final UpdateRestaurantChoiceIdUseCase updateRestaurantChoiceIdUseCase =UpdateRestaurantChoiceIdUseCase.instance;

    private final GetWorkmateByRestaurantUseCase getWorkmateByRestaurantUseCase = GetWorkmateByRestaurantUseCase.instance;

    public void updateRestaurantChoice(String restaurantId){
        updateRestaurantChoiceIdUseCase.updateRestaurantChoice(restaurantId);
    }

    public void getWorkmateByRestaurant(String restaurantId, Callback<List<Workmate>> callback){
        getWorkmateByRestaurantUseCase.getWorkmateByRestaurant(restaurantId, new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> result) {
                callback.onCallback(result);
            }
        });
    }

}
