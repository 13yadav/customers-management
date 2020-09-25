package com.strangecoder.customers.ui.addnew;

import androidx.lifecycle.ViewModel;

import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.utils.DataSource;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class AddNewViewModel extends ViewModel {

    private DataSource mDataSource;
    private String customerId;

    public AddNewViewModel(String customerId, DataSource dataSource) {
        mDataSource = dataSource;
        this.customerId = customerId;
        getCustomerById();
    }

    public Completable updateCustomer(Customer customer) {
        return mDataSource.update(customer);
    }

    public Flowable<Customer> getCustomerById() {
        return mDataSource.getById(customerId);
    }
}