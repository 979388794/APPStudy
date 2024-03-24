package com.henry.jetPackTest.Dagger2Test.singletonTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.henry.basic.R;

import javax.inject.Inject;

public class Activityfirst extends AppCompatActivity implements View.OnClickListener {
    Button button;
    @Inject
    Coffee coffee1;
    @Inject
    Coffee coffee2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityfirst);
        button = findViewById(R.id.Button_1);
        button.setOnClickListener(this::onClick);

        //方式一
//        DaggerCoffeeComponent.builder()
//                .coffeeModule(new CoffeeModule()).build()
//                .injectActivity_first(this);
//

        //方式二
//        DaggerCoffeeComponent.create().injectActivity_first(this);


        //方式三

        ((SingletonApplication)getApplication())
                .getMyCoffeeComponent()
                .injectActivity_first(this);

        Log.d("Henry", " ------" + coffee1.hashCode());
        Log.d("Henry", " ------" + coffee2.hashCode());


    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, Activitysecond.class));
    }



}