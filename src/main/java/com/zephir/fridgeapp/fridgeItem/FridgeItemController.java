package com.zephir.fridgeapp.fridgeItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fridgeitems")
public class FridgeItemController {

    @Autowired
    private FridgeItemService fridgeItemService;

    // Создание нового FridgeItem
    @PostMapping
    public ResponseEntity<FridgeItem> createFridgeItem(@RequestBody FridgeItem fridgeItem) {
        FridgeItem createdFridgeItem = fridgeItemService.createFridgeItem(fridgeItem.getFridge().getId(), fridgeItem.getFoodItem().getId(), fridgeItem.getQuantity(), Long.parseLong("1"));
        return ResponseEntity.ok(createdFridgeItem);
    }

    // Получение всех FridgeItems
    @GetMapping
    public ResponseEntity<List<FridgeItem>> getAllFridgeItems() {
        List<FridgeItem> fridgeItems = fridgeItemService.findAllFridgeItems();
        return ResponseEntity.ok(fridgeItems);
    }

    // Получение FridgeItem по ID
    @GetMapping("/{id}")
    public ResponseEntity<FridgeItem> getFridgeItemById(@PathVariable Long id) {
        FridgeItem fridgeItem = fridgeItemService.findFridgeItemById(id);
        return ResponseEntity.ok(fridgeItem);
    }

    // Обновление FridgeItem
    @PutMapping("/{id}")
    public ResponseEntity<FridgeItem> updateFridgeItem(@PathVariable Long id, @RequestBody FridgeItem fridgeItemDetails) {
        FridgeItem updatedFridgeItem = fridgeItemService.updateFridgeItem(id, fridgeItemDetails);
        return ResponseEntity.ok(updatedFridgeItem);
    }

    // Удаление FridgeItem
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFridgeItem(@PathVariable Long id) {
        fridgeItemService.deleteFridgeItem(id);
        return ResponseEntity.noContent().build();
    }
}
