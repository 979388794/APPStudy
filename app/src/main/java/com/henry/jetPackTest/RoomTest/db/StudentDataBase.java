package com.henry.jetPackTest.RoomTest.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.henry.jetPackTest.RoomTest.dao.StudentDao;
import com.henry.jetPackTest.RoomTest.entity.Student;

/**
 * @author: henry.xue
 * @date: 2024-03-24
 */

//exportSchema 导出模式
@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDataBase extends RoomDatabase {

    //暴露DAO

    public abstract StudentDao userDao();


}
