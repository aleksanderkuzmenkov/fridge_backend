package com.zephir.fridgeapp.FoodItem;

import com.zephir.fridgeapp.enums.FoodType;
import com.zephir.fridgeapp.enums.KcalUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    public FoodItem createFoodItem(String name) {
        FoodItem foodItem = new FoodItem();

        foodItem.setName(name);
//        foodItem.setKcal(kcal);
//
//        foodItem.setKcalUnit(KcalUnit.valueOf(kcalUnit.toUpperCase()));
//        foodItem.setType(FoodType.valueOf(type.toUpperCase()));

//        foodItem.setVegan(vegan);

        return foodItemRepository.save(foodItem);
    }

    // Метод для получения всех FoodItems
    public List<FoodItem> findAllFoodItems() {
        return foodItemRepository.findAll();
    }

    // Метод для поиска FoodItem по ID
    public FoodItem findFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));
    }

    // Метод для обновления FoodItem
    public FoodItem updateFoodItem(Long id, FoodItem updatedFoodItemDetails) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));

        foodItem.setName(updatedFoodItemDetails.getName());
        foodItem.setKcal(updatedFoodItemDetails.getKcal());
        foodItem.setKcalUnit(updatedFoodItemDetails.getKcalUnit());
        foodItem.setType(updatedFoodItemDetails.getType());
        foodItem.setVegan(updatedFoodItemDetails.getVegan());

        return foodItemRepository.save(foodItem);
    }

    // Метод для удаления FoodItem
    public void deleteFoodItem(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));
        foodItemRepository.delete(foodItem);
    }

    // Метод для поиска FoodItem по имени
    public Optional<FoodItem> findByName(String name) {
        return foodItemRepository.findByName(name);
    }
}