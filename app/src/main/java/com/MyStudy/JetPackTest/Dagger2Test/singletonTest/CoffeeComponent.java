package com.MyStudy.JetPackTest.Dagger2Test.singletonTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author: henry.xue
 * @date: 2024-03-18
 */
@Singleton
@Component(modules = {CoffeeModule.class})
public interface CoffeeComponent {

    void injectActivity_first(Activityfirst activityfirst );

    void injectActivity_second(Activitysecond activitysecond );

}
