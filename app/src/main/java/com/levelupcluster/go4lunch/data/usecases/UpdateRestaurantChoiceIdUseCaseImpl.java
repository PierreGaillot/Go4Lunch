package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.UserRepository;
import com.levelupcluster.go4lunch.domain.usecases.UpdateRestaurantChoiceIdUseCase;

public class UpdateRestaurantChoiceIdUseCaseImpl implements UpdateRestaurantChoiceIdUseCase {
    private final UserRepository userRepository = UserRepository.getInstance();


    @Override
    public void updateRestaurantChoice(String restaurantId) {
        userRepository.updateRestaurantChoiceId(restaurantId);
    }
}
