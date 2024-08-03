package com.foodsphere.Controller;

import com.foodsphere.model.Food;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.User;
import com.foodsphere.request.CreateFoodRequest;
import com.foodsphere.service.FoodService;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food ")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        List<Food> foods=foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/restaurantId")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,@RequestParam boolean nonVeg, @RequestParam boolean seasonal, @RequestParam(required = false) String food_Category,@PathVariable Long restaurantId,@RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        List<Food> foods=foodService.getRestaurantFood(restaurantId,vegetarian,nonVeg,seasonal,food_Category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
