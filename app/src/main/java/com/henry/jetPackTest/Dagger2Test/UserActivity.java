package com.henry.jetPackTest.Dagger2Test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.henry.basic.R;

import javax.inject.Inject;

public class UserActivity extends AppCompatActivity {
   static String Tag="Henry";
    @Inject
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        DaggerBookComponent.create().injectUser(this);
        Log.d(Tag," --"+book.hashCode());
//        BookComponent bookComponent = DaggerBookComponent.builder().build();
//        Log.d(Tag," --"+bookComponent);



    }

}