package com.strangecoder.customers.database;

import com.strangecoder.customers.utils.DataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class CustomerDataSource implements DataSource {

    private final CustomerDao customerDao;

    public CustomerDataSource(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Flowable<List<Customer>> getAll() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Completable insert(Customer customer) {
        return customerDao.insert(customer);
    }

    @Override
    public Completable update(Customer customer) {
        return customerDao.update(customer);
    }

    @Override
    public Flowable<Customer> getById(String key) {
        return customerDao.getCustomerById(key);
    }
}
