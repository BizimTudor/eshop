package com.eshop.utils;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(Long id) {
        super("Category '" + id + "' already in use");
    }
}