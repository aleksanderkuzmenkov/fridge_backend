package com.zephir.fridgeapp.FoodItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fooditems") // Base path for all endpoints in this controller
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    // Create a new FoodItem
    @PostMapping
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem) {
//        FoodItem createdFoodItem = foodItemService.createFoodItem(foodItem.getName(), foodItem.getKcal(), foodItem.getKcalUnit().name(), foodItem.getType().name(), foodItem.getVegan());
        FoodItem createdFoodItem = foodItemService.createFoodItem(foodItem.getName());
        return ResponseEntity.ok(createdFoodItem);
    }

    // Get all FoodItems
    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        List<FoodItem> foodItems = foodItemService.findAllFoodItems();
        return ResponseEntity.ok(foodItems);
    }

    // Get a single FoodItem by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        FoodItem foodItem = foodItemService.findFoodItemById(id);
        return ResponseEntity.ok(foodItem);
    }

    // Update a FoodItem
    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItemDetails) {
        FoodItem updatedFoodItem = foodItemService.updateFoodItem(id, foodItemDetails);
        return ResponseEntity.ok(updatedFoodItem);
    }

    // Delete a FoodItem
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}