package com.henry.jetPackTest.Dagger2Test;

import dagger.Module;
import dagger.Provides;


/**
 * @author: henry.xue
 * @date: 2024-03-18
 */
//APT技术，Arouter、DataBinding、Room,基本上都是APT编译器生成代码
@Module
public class BookModule {

    @Provides
    public Book GetBook() {
        return new Book();
    }

}
