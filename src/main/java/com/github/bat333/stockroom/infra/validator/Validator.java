package com.github.bat333.stockroom.infra.validator;


public interface Validator<T> {
    T validator(Long id);
}
