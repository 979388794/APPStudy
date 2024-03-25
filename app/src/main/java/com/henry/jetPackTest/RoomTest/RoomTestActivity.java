package com.henry.jetPackTest.RoomTest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.henry.basic.databinding.ActivityViewmodelBinding;
import com.henry.jetPackTest.RoomTest.dao.StudentDao;
import com.henry.jetPackTest.RoomTest.db.StudentDataBase;
import com.henry.jetPackTest.RoomTest.entity.Student;

import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-03-24
 */
public class RoomTestActivity extends AppCompatActivity {

    ActivityViewmodelBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewmodelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //数据库的操作应该是在子线程
        DbTest t = new DbTest();
        t.start();
    }

    //测试刚刚写的三个角色Room数据库  增删改查
    class DbTest extends Thread {
        @Override
        public void run() {
            super.run();
            //数据库的操作都在这里
            StudentDataBase henryDB = Room.databaseBuilder
                            (getApplicationContext(), StudentDataBase.class, "Henry")
                    .allowMainThreadQueries()//可以在主线程执行查询，不建议这么做
                    .fallbackToDestructiveMigration()//数据库改变时，强制升级时，不报错
                    .build();
            StudentDao dao = henryDB.userDao();

            dao.insert(new Student("henry0", "123", 1));
            dao.insert(new Student("henry1", "456", 2));
            dao.insert(new Student("henry2", "789", 3));
            dao.insert(new Student("henry3", "101112", 4));

            //查看全部数据
            List<Student> all = dao.getall();
            Log.d("Henry00", "all = " + all.toString());
            Log.d("Henry00", "---------------------------------");
            //查询名字为henry1的一条数据
            Student henry1 = dao.findByName("henry1");
            Log.d("Henry00", "henry1 查询 = " + henry1.toString());
            Log.d("Henry00", "---------------------------------");

            //查询id为2，3，4的数组数据
            List<Student> allID = dao.getallId(new int[]{2, 3, 4});
            Log.d("Henry00", "allID 查询 = " + allID.toString());
        }

    }

}
