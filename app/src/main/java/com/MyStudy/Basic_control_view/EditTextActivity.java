package com.MyStudy.Basic_control_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextActivity extends AppCompatActivity {
    private Button login;
    private EditText Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //跳转到Button演示界面
                Toast.makeText(EditTextActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(HotFixActivity.this,EditTextActivity.class);
                //startActivity(intent);
            }
        });
        Username = findViewById(R.id.et1);
        Username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("xczxcz", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}