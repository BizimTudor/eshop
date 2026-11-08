package com.eshop.product;

import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecs {
    private ProductSpecs() {}

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> inCategory(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("id"), categoryId);
    }
}