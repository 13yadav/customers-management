package com.strangecoder.customers.ui.customers;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.strangecoder.customers.utils.DataSource;
import com.strangecoder.customers.database.Customer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class CustomerListViewModel extends AndroidViewModel {

    private DataSource dataSource;
    private Customer customer;

    public CustomerListViewModel(DataSource dataSource, Application application) {
        super(application);
        this.dataSource = dataSource;
        getCustomers();
    }

    public Flowable<List<Customer>> getCustomers(){
        return dataSource.getAll();
    }

    public Completable onAddClicked(){
        this.customer = new Customer();
        return dataSource.insert(customer);
    }

    public String getCustomerId(){
        return customer.customerId;
    }
}