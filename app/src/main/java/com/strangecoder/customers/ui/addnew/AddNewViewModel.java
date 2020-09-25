package com.strangecoder.customers.ui.addnew;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.utils.DataSource;
import com.strangecoder.customers.ui.customers.CustomerListViewModel;

import io.reactivex.Completable;

public class AddNewViewModel extends AndroidViewModel {

    private DataSource mDataSource;
    Customer mCustomer;

    public AddNewViewModel(DataSource dataSource, Application application) {
        super(application);
        mDataSource = dataSource;
    }


    public Completable updateCustomer(Customer customer){
        return mDataSource.update(customer);
    }
}