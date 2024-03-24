package com.henry.jetPackTest.Dagger2Test.singletonTest;

import android.app.Application;

/**
 * @author: henry.xue
 * @date: 2024-03-18
 */
public class SingletonApplication extends Application {
    private CoffeeComponent myCoffeeComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        myCoffeeComponent = DaggerCoffeeComponent.builder()
                .coffeeModule(new CoffeeModule())
                .build();
    }

    public CoffeeComponent getMyCoffeeComponent() {
        return myCoffeeComponent;
    }


}
