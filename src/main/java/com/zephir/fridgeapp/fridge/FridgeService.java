package com.zephir.fridgeapp.fridge;

import com.zephir.fridgeapp.user.User;
import com.zephir.fridgeapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FridgeService {
    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private UserRepository userRepository;

    public Fridge createFridge(String fridgeName, Long userId) {
        String serialNumber = generateUniqueSerialNumber();

        Fridge fridge = new Fridge(fridgeName, serialNumber);

        fridge = fridgeRepository.save(fridge);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFridge(fridge);
        userRepository.save(user);

        return fridge;
    }

    private String generateUniqueSerialNumber() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        return uuid.toString();
    }
}
