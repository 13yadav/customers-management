package com.strangecoder.customers.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "customers")
public class Customer {
    @NonNull
    @PrimaryKey
    public String customerId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "contactPerson")
    public String contactPerson;

    @ColumnInfo(name = "contactPersonRole")
    public String contactPersonRole;

    @ColumnInfo(name = "contactPersonEmail")
    public String contactPersonEmail;

    @ColumnInfo(name = "contactPersonPhone")
    public String contactPersonPhone;


    public Customer(@NonNull String customerId, String name, String address, String phone, String contactPerson, String contactPersonRole, String contactPersonEmail, String contactPersonPhone) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.contactPerson = contactPerson;
        this.contactPersonRole = contactPersonRole;
        this.contactPersonEmail = contactPersonEmail;
        this.contactPersonPhone = contactPersonPhone;
    }

    @Ignore
    public Customer() {
        this.customerId = UUID.randomUUID().toString();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonRole() {
        return contactPersonRole;
    }

    public void setContactPersonRole(String contactPersonRole) {
        this.contactPersonRole = contactPersonRole;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    static final String[] CUSTOMERS = {
            "Master Enterprises",
            "One Sales Corporation",
            "Ray Solar",
            "Max Pumps Pvt. Ltd.",
            "SSR Industries"
    };
}
