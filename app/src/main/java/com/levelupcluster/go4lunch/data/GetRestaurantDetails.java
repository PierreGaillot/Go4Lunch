package com.levelupcluster.go4lunch.data;

import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.utils.Callback;
import com.levelupcluster.go4lunch.utils.DataParser;
import com.levelupcluster.go4lunch.utils.JsonTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetRestaurantDetails {

    private static final String API_KEY = "AIzaSyCEDe9eFsSku4dpBjkjVjqfXjUsL5lQixg";

    public List<String> parameters = new ArrayList<>(Arrays.asList(
            "name",
            "rating",
            "formatted_phone_number",
            "formatted_address",
            "website",
            "photo"
            /**
             *             "photo",
             *             "type",
             *             "url",
             *             "vicinity",
             *             "formatted_address",
             *             "opening_hours",
             *             "website",
             *             "editorial_summary",
             *             "dine_in"
             */


    ));

    public void getDetails(String restaurantId, Callback<RestaurantDetails> callback){
        String url = getUrl(restaurantId);
        downloadUrl(url, result -> {
            RestaurantDetails restaurantDetails;
            DataParser dataParser = new DataParser();
            restaurantDetails = dataParser.parseRestaurantDetails(result);
            restaurantDetails.setId(restaurantId);
            callback.onCallback(restaurantDetails);
        });
    }

    public String getUrl(String placeId) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?fields=");

        for (int i = 0; i < parameters.size();  i++) {
            if ( i+1 != parameters.size()) {
                url.append(parameters.get(i)).append("%2C");
            } else {
                url.append(parameters.get(i));
            }
        }

        url.append("&place_id=").append(placeId);
        url.append("&key=" + API_KEY);
        System.out.println(url.toString());
        return url.toString();

    }

    protected void downloadUrl(String url, Callback<String> callback) {
        new JsonTask(callback).execute(url);
    }
}
