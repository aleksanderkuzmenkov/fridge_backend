package com.zephir.fridgeapp.fridge;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Fridge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String serialNumber;

    public Fridge(String name, String serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }
}