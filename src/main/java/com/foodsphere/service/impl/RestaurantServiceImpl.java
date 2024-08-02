package com.foodsphere.service.impl;

import com.foodsphere.dto.RestaurantDto;
import com.foodsphere.model.Address;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.User;
import com.foodsphere.repository.AddressRepository;
import com.foodsphere.repository.RestaurantRepository;
import com.foodsphere.repository.UserRepository;
import com.foodsphere.request.CreateRestaurantRequest;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address=addressRepository.save(req.getAddress());

        Restaurant restaurant=new Restaurant();

        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setName(req.getName());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);

        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }

        if(restaurant.getDescription()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {

        return getAllRestaurant();
    }

    @Override
    public List<Restaurant> searchRestaurant(String key) {
        return restaurantRepository.findBySearchQuery(key);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("restaurant not found with id"+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception{
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);

        if(restaurant==null){
            throw new Exception("Restaurant not found with userId"+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addFavourites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant=findRestaurantById(restaurantId);

        RestaurantDto dto=new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        if(user.getFavourites().contains(dto)){
            user.getFavourites().remove(dto);
        }else {
            user.getFavourites().add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
