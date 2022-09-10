package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private int id;
    private String title;
    @JsonProperty("discountPercentage")
    private double discountPercentage;

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
    
    public double getDiscountPercentage() {
        return discountPercentage;
    }
}
