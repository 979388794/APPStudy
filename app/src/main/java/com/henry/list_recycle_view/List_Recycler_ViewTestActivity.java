package com.henry.list_recycle_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.henry.list_recycle_view.RecyclerView.RecyclerTest1;
import com.henry.list_recycle_view.RecyclerView.RecyclerTest3;
import com.henry.list_recycle_view.listview.ListViewTest2;
import com.henry.basic.R;
import com.henry.list_recycle_view.RecyclerView.RecyclerTest2;
import com.henry.list_recycle_view.listview.ListViewTest1;

public class List_Recycler_ViewTestActivity extends AppCompatActivity implements View.OnClickListener {

    Button List1;
    Button List2;
  //  Button List3;

    Button Recy1;
    Button Recy2;
    Button Recy3;
    Button Recy4;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recycler_view_test);
        findButton();
        setListeners();
    }

    private void setListeners() {
        List1.setOnClickListener(this);
        List2.setOnClickListener(this);
      //  List3.setOnClickListener(this);
        Recy1.setOnClickListener(this);
        Recy2.setOnClickListener(this);
        Recy3.setOnClickListener(this);
        Recy4.setOnClickListener(this);
    }

    private void findButton() {
        List1 = findViewById(R.id.list_1);
        List2 = findViewById(R.id.list_2);
      //  List3 = findViewById(R.id.list_3);
        Recy1 = findViewById(R.id.Recycler_1);
        Recy2 = findViewById(R.id.Recycler_2);
        Recy3 = findViewById(R.id.Recycler_3);
        Recy4 = findViewById(R.id.Recycler_4);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.list_1:
                intent = new Intent(this, ListViewTest1.class);
                break;
            case R.id.list_2:
                intent = new Intent(this, ListViewTest2.class);
                break;
            case R.id.Recycler_1:
                intent = new Intent(this, RecyclerTest1.class);
                break;
            case R.id.Recycler_2:
                intent = new Intent(this, RecyclerTest2.class);
                break;
            case R.id.Recycler_3:
                intent = new Intent(this, RecyclerTest3.class);
                break;
            case R.id.Recycler_4:
                intent = new Intent(this, RecyclerTest3.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        startActivity(intent);
    }


}