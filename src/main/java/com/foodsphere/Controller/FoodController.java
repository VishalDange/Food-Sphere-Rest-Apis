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
@RequestMapping("/api/food")
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

//    @GetMapping("/restaurant/{restaurantId}")
//    public ResponseEntity<List<Food>> getRestaurantFood(
//            @PathVariable Long restaurantId,
//            @RequestParam boolean vegetarian,
//            @RequestParam boolean nonveg,
//            @RequestParam boolean seasonal,
//            @RequestParam(required = false) String food_category,
//            @RequestHeader("Authorization") String jwt) throws Exception {
//
//        User user = userService.findUserByJwtToken(jwt);
//        List<Food> foods = foodService.getRestaurantFood(restaurantId, vegetarian, nonveg, seasonal, food_category);
//
//        return new ResponseEntity<>(foods, HttpStatus.OK);
//    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) Boolean nonveg,
            @RequestParam(required = false) Boolean seasonal,
            @RequestParam(required = false) String food_category,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantFood(restaurantId,
                Boolean.TRUE.equals(vegetarian),
                Boolean.TRUE.equals(nonveg),
                Boolean.TRUE.equals(seasonal),
                food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
