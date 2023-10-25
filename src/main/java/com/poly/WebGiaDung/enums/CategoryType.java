package com.poly.WebGiaDung.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryType {
    PRODUCT("product"), SERVICE("service");
    private String value;
    CategoryType(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return this.value;
    }
}
