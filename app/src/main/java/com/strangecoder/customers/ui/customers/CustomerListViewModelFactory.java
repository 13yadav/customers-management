package com.strangecoder.customers.ui.customers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.strangecoder.customers.utils.DataSource;

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the CustomerDao and context to the ViewModel.
 */
public class CustomerListViewModelFactory implements ViewModelProvider.Factory {
    private DataSource dataSource;
    private Application application;

    public CustomerListViewModelFactory(DataSource dataSource, Application application){
        this.dataSource = dataSource;
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CustomerListViewModel.class)) {
            return (T) new CustomerListViewModel(dataSource, application);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
