package com.foodsphere.Controller;

import com.foodsphere.model.IngredientCategory;
import com.foodsphere.model.IngredientsItem;
import com.foodsphere.request.IngredientCategoryRequest;
import com.foodsphere.request.IngredientRequest;
import com.foodsphere.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception {

        IngredientCategory item=ingredientsService.createIngredientCategory(request.getName(),request.getRestaurantId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest request) throws Exception {

        IngredientsItem item=ingredientsService.createIngredientItem(request.getRestaurantId(),request.getName(),request.getCategoryId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStoke(@PathVariable Long id) throws Exception {

        IngredientsItem item=ingredientsService.updateStock(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable Long id) throws Exception {

        List<IngredientsItem> item=ingredientsService.findRestaurantIngredients(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {

        List<IngredientCategory> item=ingredientsService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }


}
