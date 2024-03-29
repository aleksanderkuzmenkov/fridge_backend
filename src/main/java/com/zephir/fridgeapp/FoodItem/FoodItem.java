package com.zephir.fridgeapp.FoodItem;


import com.zephir.fridgeapp.enums.FoodType;
import com.zephir.fridgeapp.enums.KcalUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private Long id;

    private String name;
    private Double kcal;

    @Enumerated(EnumType.STRING) // Сохраняем перечисление как строку
    private KcalUnit kcalUnit;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    private Boolean vegan;

}