package com.zephir.fridgeapp.user;

import com.zephir.fridgeapp.fridge.Fridge;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    @NaturalId(mutable = true)
    private String email;
    private String role;
    @NonNull
    private boolean isEnabled;
    @OneToOne
    @JoinColumn(name = "fridge_id", referencedColumnName = "id")
    private Fridge fridge;
}
