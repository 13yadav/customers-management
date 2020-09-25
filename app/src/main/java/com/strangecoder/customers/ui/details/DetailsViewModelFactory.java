package com.strangecoder.customers.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.strangecoder.customers.utils.DataSource;

public class DetailsViewModelFactory implements ViewModelProvider.Factory {

    private String customerId;
    private DataSource dataSource;

    public DetailsViewModelFactory(String customerId, DataSource dataSource) {
        this.customerId = customerId;
        this.dataSource = dataSource;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(customerId, dataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
