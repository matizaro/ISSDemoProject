package com.example.matiz.issdemoproject.repository.models.validators;

public interface Validate<T> {
    boolean isValid(T object);
}
