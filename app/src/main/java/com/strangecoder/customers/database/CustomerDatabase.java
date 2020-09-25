package com.strangecoder.customers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Customer.class}, version = 2)
public abstract class CustomerDatabase extends RoomDatabase {

    public abstract CustomerDao customerDao();

    private static volatile CustomerDatabase INSTANCE = null;

    public static synchronized CustomerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CustomerDatabase.class,
                    "customer-database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

