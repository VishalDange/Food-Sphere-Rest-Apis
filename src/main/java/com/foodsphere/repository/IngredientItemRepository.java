package com.foodsphere.repository;

import com.foodsphere.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem,Long> {

    public List<IngredientsItem> findByRestaurantId(Long restaurantId);


}
