package com.strangecoder.customers;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.database.CustomerDao;
import com.strangecoder.customers.database.CustomerDatabase;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CustomerDatabaseTest {
    private CustomerDao customerDao;
    private CustomerDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CustomerDatabase.class).build();
        customerDao = db.customerDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
//        Customer customer = new Customer();
//        customer.setCustomerId(1);
//        customer.setName("Strange Coder");
//        customer.setContactPerson("Ranjit Yadav");
//        customerDao.insert(customer);
    }
}
