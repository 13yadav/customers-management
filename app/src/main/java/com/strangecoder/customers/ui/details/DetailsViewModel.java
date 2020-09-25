package com.strangecoder.customers.ui.details;

import androidx.lifecycle.ViewModel;

import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.utils.DataSource;

import io.reactivex.Flowable;

public class DetailsViewModel extends ViewModel {

    private String customerId;
    private DataSource dataSource;

    public DetailsViewModel(String customerId, DataSource dataSource) {
        this.customerId = customerId;
        this.dataSource = dataSource;
        getCustomerById();
    }

    public Flowable<Customer> getCustomerById(){
        return dataSource.getById(customerId);
    }

}