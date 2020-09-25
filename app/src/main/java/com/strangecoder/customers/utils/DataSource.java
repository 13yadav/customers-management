package com.strangecoder.customers.utils;

import com.strangecoder.customers.database.Customer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DataSource {

    Flowable<List<Customer>> getAll();

    Completable insert(Customer customer);

    Completable update(Customer customer);

    Flowable<Customer> getById(String key);
}
