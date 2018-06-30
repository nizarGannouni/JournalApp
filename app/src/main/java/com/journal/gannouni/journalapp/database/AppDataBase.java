package com.journal.gannouni.journalapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {DiaryEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDataBase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Diary_db";
    private static AppDataBase sAppDataBase;

    public static AppDataBase getInstance(Context context) {
        if (sAppDataBase == null) {
            synchronized (LOCK) {
                sAppDataBase = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                        AppDataBase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sAppDataBase;
    }

    public abstract DiaryDao mDiaryDao();
}
