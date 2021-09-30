package com.main.model;

import com.main.annotation.FuelCheckIsValid;

import javax.validation.constraints.Positive;


public class FuelCheck {
    @Positive
    private Long id;
    @FuelCheckIsValid
    private String code;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FuelCheck(@Positive Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
