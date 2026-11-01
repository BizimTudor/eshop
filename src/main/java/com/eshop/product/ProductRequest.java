package com.eshop.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @Min(0) int stock
) {}
