package com.dreamernguyen.AppRaoVatSaFaCo.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;

@Database(entities = {TimKiem.class},version = 1)
public abstract class TimKiemDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "lichSuTimKiem";

    private static TimKiemDataBase instance;

    public static synchronized TimKiemDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TimKiemDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract TimKiemDAO timKiemDAO();

    ;
}
