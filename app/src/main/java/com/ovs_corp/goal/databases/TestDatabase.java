package com.ovs_corp.goal.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TestDB.class},version = 1)
public abstract class TestDatabase extends RoomDatabase {

    // TODO: 6/8/2020 Normal Database (It will be added at 6/10/2020)

    public static TestDatabase instance;

    public abstract TestDao testDao() ;

    public static synchronized TestDatabase getInstance(Context context){

        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TestDatabase.class, "test_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
        }

        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(instance).execute();
            }
        };

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
            private TestDao testDao;

            private PopulateDbAsyncTask(TestDatabase db)
            {
                testDao = db.testDao();

            }
            @Override
            protected Void doInBackground(Void... voids) {
                testDao.insert(new TestDB("queston", "solution", "answer"));
                return null;
            }
        }

 }



