package com.ovs_corp.goal.databases;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TestDao {

    // TODO: 6/8/2020 Normal Database (It will be added at 6/10/2020)

    @Insert
    void insert(TestDB testDB);

    @Update
    void update(TestDB testDB);

    @Delete
    void delete(TestDB testDB);

    @Query("DELETE FROM test_table")
    void deleteAll();

    @Query("SELECT * FROM test_table ORDER BY id DESC")
    LiveData<List<TestDB>> getAllTask();
}
