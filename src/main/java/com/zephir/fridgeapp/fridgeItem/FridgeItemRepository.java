package com.zephir.fridgeapp.fridgeItem;

import com.zephir.fridgeapp.fridgeItem.FridgeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem, Long> {

}