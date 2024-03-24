package com.henry.jetPackTest.Dagger2Test.singletonTest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author: henry.xue
 * @date: 2024-03-18
 */

@Module
public class CoffeeModule {
    @Singleton
    @Provides
    public Coffee GetCoffee() {
        return new Coffee();
    }
}

