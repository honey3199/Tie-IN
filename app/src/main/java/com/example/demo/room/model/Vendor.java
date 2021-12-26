package com.example.demo.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "vendor")
public class Vendor {
    @PrimaryKey
    @NonNull
    String id;
    String name;
    String shop;
    String address;
    String logo;
    String productType;
    String email;
    String phone;
    Float rating;

    public Vendor(@NonNull String id, String name, String shop, String address, String logo, String productType, String email, String phone, Float rating) {
        this.id = id;
        this.name = name;
        this.shop = shop;
        this.address = address;
        this.logo = logo;
        this.productType = productType;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getShop() {
        return shop;
    }

    public String getProductType() {
        return productType;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Float getRating() {
        return rating;
    }
}
