package com.strangecoder.customers.utils;


import android.content.Context;

import com.strangecoder.customers.database.CustomerDataSource;
import com.strangecoder.customers.database.CustomerDatabase;
import com.strangecoder.customers.ui.addnew.AddNewViewModel;

/**
 * Enables injection of data sources.
 */
public class Injection {

    public static DataSource provideCustomerDataSource(Context context) {
        CustomerDatabase database = CustomerDatabase.getInstance(context);
        return new CustomerDataSource(database.customerDao());
    }
}
