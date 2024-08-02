package com.foodsphere.Controller;

import com.foodsphere.model.Restaurant;
import com.foodsphere.model.User;
import com.foodsphere.request.CreateRestaurantRequest;
import com.foodsphere.response.MessageResponse;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.createRestaurant(request,user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurant(id,request);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("Restaurant deleted Successfully..");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
}
