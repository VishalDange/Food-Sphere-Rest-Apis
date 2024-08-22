package com.foodsphere.service.impl;

import com.foodsphere.model.IngredientCategory;
import com.foodsphere.model.IngredientsItem;
import com.foodsphere.model.Restaurant;
import com.foodsphere.repository.IngredientCategoryRepository;
import com.foodsphere.repository.IngredientItemRepository;
import com.foodsphere.service.IngredientsService;
import com.foodsphere.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory=new IngredientCategory();

        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory>  ingredientCategory=ingredientCategoryRepository.findById(id);

        if(ingredientCategory.isEmpty()){
            throw new Exception("Ingredient Category not Found...");
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);

        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category=findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem=new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(category);

        IngredientsItem item=ingredientItemRepository.save(ingredientsItem);

        category.getIngredientsItems().add(item);
        return item;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientCategory updateIngredientCategory(Long categoryId, String newName) throws Exception {
        Optional<IngredientCategory> optionalCategory = ingredientCategoryRepository.findById(categoryId);

        if (optionalCategory.isEmpty()) {
            throw new Exception("Ingredient Category not found with id: " + categoryId);
        }

        IngredientCategory category = optionalCategory.get();
        category.setName(newName);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> optionalIngredientsItem=ingredientItemRepository.findById(id);

        if(optionalIngredientsItem.isEmpty()){
            throw  new Exception("Ingredient not found");
        }

        IngredientsItem ingredientsItem=optionalIngredientsItem.get();

        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
