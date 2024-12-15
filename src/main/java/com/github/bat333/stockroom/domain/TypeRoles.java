package com.github.bat333.stockroom.domain;

public enum TypeRoles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String values;

    TypeRoles(String values){
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}
