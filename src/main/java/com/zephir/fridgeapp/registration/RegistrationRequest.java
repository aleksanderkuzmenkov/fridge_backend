package com.zephir.fridgeapp.registration;

import org.hibernate.annotations.NaturalId;

public record RegistrationRequest(
         String name,
         String password,
         String email,
         boolean isEnabled,
         String role
) {
}
