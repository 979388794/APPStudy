package com.henry.jetPackTest.RoomTest.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.henry.jetPackTest.RoomTest.entity.Student;

import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-03-24
 */

@Dao
public interface StudentDao {

    @Insert
    void insert(Student... student);

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("select * from Student")
    List<Student> getall();

    //查询一条
    @Query("select * from Student where name like:name")
    Student findByName(String name);


    //查询数组多个记录
    @Query("select * from Student where uid in(:userIds)")
    List<Student> getallId(int[] userIds);







}
