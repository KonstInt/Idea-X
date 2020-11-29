package com.ovs_corp.bstorm.databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TestRepository {

    // TODO: 6/8/2020 Normal Database (It will be added at 6/10/2020)
    private TestDao testDao;
    private LiveData<List<TestDB>> allTest;

    public TestRepository(Application application){

        TestDatabase database = TestDatabase.getInstance(application);
        testDao = database.testDao();
        allTest = testDao.getAllTask();
    }

    public void insert(TestDB testDB){
        new InsertTestAsyncTask(testDao).execute(testDB);
    }

    public void update(TestDB testDB){
        new UpdateTestAsyncTask(testDao).execute(testDB);
    }

    public void delete(TestDB testDB){
        new DeleteTestAsyncTask(testDao).execute(testDB);
    }

    public void deleteAllNotes(){
        new DeleteAllTestAsyncTask(testDao).execute();
    }

    public LiveData<List<TestDB>> getAllTest(){
        return allTest;
    }


    //////////////////////////////////////////////////
    /////////////////////////////////////////////////

    private static class  InsertTestAsyncTask extends AsyncTask <TestDB, Void, Void>{
        private TestDao testDao;

        private InsertTestAsyncTask(TestDao testDao){
            this.testDao = testDao;
        }


        @Override
        protected Void doInBackground(TestDB... testDBS) {
            testDao.insert(testDBS[0]);
            return null;
        }
    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////

    private static class  UpdateTestAsyncTask extends AsyncTask <TestDB, Void, Void>{
        private TestDao testDao;

        private UpdateTestAsyncTask(TestDao testDao){
            this.testDao = testDao;
        }


        @Override
        protected Void doInBackground(TestDB... testDBS) {
            testDao.update(testDBS[0]);
            return null;
        }
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////

    private static class  DeleteTestAsyncTask extends AsyncTask <TestDB, Void, Void>{
        private TestDao testDao;

        private DeleteTestAsyncTask(TestDao testDao){
            this.testDao = testDao;
        }


        @Override
        protected Void doInBackground(TestDB... testDBS) {
            testDao.delete(testDBS[0]);
            return null;
        }
    }


    //////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////

    private static class  DeleteAllTestAsyncTask extends AsyncTask <Void, Void, Void>{
        private TestDao testDao;

        private DeleteAllTestAsyncTask(TestDao testDao){
            this.testDao = testDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            testDao.deleteAll();
            return null;
        }
    }
}
