package com.zephir.fridgeapp.fridgeItem;

import com.zephir.fridgeapp.FoodItem.FoodItem;
import com.zephir.fridgeapp.FoodItem.FoodItemRepository;
import com.zephir.fridgeapp.fridge.Fridge;
import com.zephir.fridgeapp.fridge.FridgeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeItemService {
    @Autowired
    private FridgeItemRepository fridgeItemRepository;
    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private FoodItemRepository foodItemRepository;

    // Создание нового FridgeItem
    public FridgeItem createFridgeItem(Long fridgeId, Long foodItemId, Integer quantity, Long userId) {
        // Получаем холодильник по ID
        Fridge fridge = fridgeRepository.findById(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("Холодильник с ID " + fridgeId + " не найден"));

        // Получаем продукт по ID
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с ID " + foodItemId + " не найден"));

        // Создаем новую запись FridgeItem
        FridgeItem fridgeItem = new FridgeItem();
        fridgeItem.setFridge(fridge);
        fridgeItem.setFoodItem(foodItem);
        fridgeItem.setQuantity(quantity);

        return fridgeItemRepository.save(fridgeItem);
    }

    // Получение всех FridgeItems
    public List<FridgeItem> findAllFridgeItems() {
        return fridgeItemRepository.findAll();
    }

    // Поиск FridgeItem по ID
    public FridgeItem findFridgeItemById(Long id) {
        return fridgeItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FridgeItem not found"));
    }

    // Обновление FridgeItem
    public FridgeItem updateFridgeItem(Long id, FridgeItem updatedFridgeItemDetails) {
        FridgeItem fridgeItem = findFridgeItemById(id);

        // Обновление полей
        fridgeItem.setQuantity(updatedFridgeItemDetails.getQuantity());
        // И другие поля по необходимости

        return fridgeItemRepository.save(fridgeItem);
    }

    // Удаление FridgeItem
    public void deleteFridgeItem(Long id) {
        FridgeItem fridgeItem = findFridgeItemById(id);
        fridgeItemRepository.delete(fridgeItem);
    }
}
