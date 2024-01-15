package com.zephir.fridgeapp.fridge;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fridges")
@RequiredArgsConstructor
public class FridgeController {
    @Autowired
    private FridgeService fridgeService;

    @PostMapping
    public ResponseEntity<Fridge> createFridge(@RequestBody Map<String, String> fridgeData){
        Fridge fridge = fridgeService.createFridge(fridgeData.get("name"), Long.parseLong(fridgeData.get("userId")));
        return ResponseEntity.ok(fridge);
    }
}