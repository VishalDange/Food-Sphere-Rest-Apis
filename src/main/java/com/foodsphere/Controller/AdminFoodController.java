package com.foodsphere.Controller;

import com.foodsphere.model.Food;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.User;
import com.foodsphere.request.CreateFoodRequest;
import com.foodsphere.response.MessageResponse;
import com.foodsphere.service.FoodService;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food=foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);

        MessageResponse response=new MessageResponse();
        response.setMessage("Food Deleted Successfully...");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        Food food=foodService.updateAvailability(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
