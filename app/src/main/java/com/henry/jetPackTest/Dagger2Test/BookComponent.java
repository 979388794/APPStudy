package com.henry.jetPackTest.Dagger2Test;

import dagger.Component;

/**
 * @author: henry.xue
 * @date: 2024-03-18
 */

//快递员
@Component(modules = BookModule.class)
public interface BookComponent {

    //注入用户收获地址
    void injectUser(UserActivity userActivity);


    @Component.Builder
    interface Builder {
        BookComponent build();
    }

}
