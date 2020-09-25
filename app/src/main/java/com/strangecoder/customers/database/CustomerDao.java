package com.strangecoder.customers.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CustomerDao {
    @Query("SELECT * FROM customers")
    Flowable<List<Customer>> getAllCustomers();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(Customer customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Customer customer);

    @Query("SELECT * FROM customers WHERE customerId = :key")
    Flowable<Customer> getCustomerById(String key);

}
