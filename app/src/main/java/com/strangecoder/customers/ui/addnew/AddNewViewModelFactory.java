package com.strangecoder.customers.ui.addnew;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.strangecoder.customers.ui.customers.CustomerListViewModel;
import com.strangecoder.customers.utils.DataSource;

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 * <p>
 * Provides the CustomerDao and context to the ViewModel.
 */
public class AddNewViewModelFactory implements ViewModelProvider.Factory {

    private DataSource mDataSource;
    private String customerId;

    public AddNewViewModelFactory(String customerId, DataSource dataSource) {
        mDataSource = dataSource;
        this.customerId = customerId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddNewViewModel.class)) {
            return (T) new AddNewViewModel(customerId, mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
