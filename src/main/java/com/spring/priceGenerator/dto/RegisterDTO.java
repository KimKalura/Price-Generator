package com.spring.priceGenerator.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegisterDTO {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Email(message = "Email provided is not valid")
    private String email;

    @NotBlank(message = "Country is mandatory")
    private String country;

    private String role;

    public RegisterDTO(String username, String password, String email, String country, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
