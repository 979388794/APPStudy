package com.henry.jetPackTest.navigationTest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-03-26
 */
public class NavigationTestActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationtest);
        //通过 findViewById 方法找到布局文件中 ID 为 nav_view 的 BottomNavigationView 控件，并将其赋值给 bottomNavigationView 变量。
        bottomNavigationView = findViewById(R.id.nav_view);
        //NavHostFragment 是用于承载导航图的容器。
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        //获取与 NavHostFragment 关联的 NavController，用于管理应用程序的导航操作。
        NavController navController = navHostFragment.getNavController();
        //与 navController 关联起来，使得底部导航栏中的菜单项可以与导航控制器进行交互，实现页面之间的导航。
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
