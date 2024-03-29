package com.zephir.fridgeapp.fridge;

import com.zephir.fridgeapp.FoodItem.FoodItem;
import com.zephir.fridgeapp.FoodItem.FoodItemService;
import com.zephir.fridgeapp.fridgeItem.FridgeItem;
import com.zephir.fridgeapp.fridgeItem.FridgeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/fridges")
@RequiredArgsConstructor
public class FridgeController {
    @Autowired
    private FridgeService fridgeService;
    private final FoodItemService foodItemService;
    private final FridgeItemService fridgeItemService;

    @PostMapping
    public ResponseEntity<Fridge> createFridge(@RequestBody Map<String, String> fridgeData){

        Fridge fridge = fridgeService.createFridge(fridgeData.get("name"), Long.parseLong(fridgeData.get("userId")));
        return ResponseEntity.ok(fridge);
    }

    @PostMapping("/addFoodItemToFridge")
    public ResponseEntity<?> addFoodItemToFridge(@RequestBody Map<String, Object> requestData) {

        System.out.println(requestData);


        String productName = (String) requestData.get("productName");
        Long fridgeId = Long.parseLong((String) requestData.get("fridgeId"));
        Long userId = Long.parseLong((String) requestData.get("userId"));
        Integer quantity = (Integer) requestData.get("quantity");

        System.out.println(fridgeId);

        Optional<FoodItem> existingFoodItem = foodItemService.findByName(productName);

        FoodItem foodItem = existingFoodItem.orElseGet(() -> foodItemService.createFoodItem(
                productName
        ));

        // Создаем FridgeItem с ID существующего или нового FoodItem и ID холодильника
        FridgeItem fridgeItem = fridgeItemService.createFridgeItem(fridgeId, foodItem.getId(), quantity, userId);

        return ResponseEntity.ok(fridgeItem);
    }
}