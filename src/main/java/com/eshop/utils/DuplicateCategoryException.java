package com.eshop.utils;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException(String name) {
        super("Category '" + name + "' already exists");
    }
}
