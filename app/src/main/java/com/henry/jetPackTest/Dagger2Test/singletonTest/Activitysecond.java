package com.henry.jetPackTest.Dagger2Test.singletonTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.henry.basic.R;

import javax.inject.Inject;

public class Activitysecond extends AppCompatActivity {

    @Inject
    Coffee coffee3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitysecond);

        //方式一
//        DaggerCoffeeComponent.builder()
//                .coffeeModule(new CoffeeModule()).build()
//                .injectActivity_second(this);


//        //方式二
//        DaggerCoffeeComponent.create().injectActivity_second(this);


        //方式三
        ((SingletonApplication) getApplication())
                .getMyCoffeeComponent()
                .injectActivity_second(this);

        Log.d("Henry", " ------" + coffee3.hashCode());
    }

}