
package com.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userId",
    "firstName",
    "lastName",
    "email",
    "carId"
})
public class User {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("userId")
    private String userId = null;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("firstName")
    private String firstName = null;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastName")
    private String lastName = null;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    private String email = null;
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
    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

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

    public User withCarId(String carId) {
        this.carId = carId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("firstName", firstName).append("lastName", lastName).append("email", email).append("carId", carId).toString();
    }

}
