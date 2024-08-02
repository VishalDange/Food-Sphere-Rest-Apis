package com.foodsphere.service;

import com.foodsphere.dto.RestaurantDto;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.User;
import com.foodsphere.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String key);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
