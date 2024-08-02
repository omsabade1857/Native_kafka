
package com.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "carId",
    "name",
    "type",
    "price"
})
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("carId")
    private String carId = null;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name = null;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private String type = null;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("price")
    private Object price = null;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("carId")
    public String getCarId() {
        return carId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("carId")
    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Car withCarId(String carId) {
        this.carId = carId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Car withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Car withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("price")
    public Object getPrice() {
        return price;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("price")
    public void setPrice(Object price) {
        this.price = price;
    }

    public Car withPrice(Object price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("carId", carId).append("name", name).append("type", type).append("price", price).toString();
    }

}
