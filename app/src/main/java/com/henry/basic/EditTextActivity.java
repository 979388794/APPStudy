package com.henry.basic;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
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

        login.setKeyListener(new KeyListener() {
            @Override
            public int getInputType() {
                return 0;
            }

            @Override
            public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
                return false;
            }

            @Override
            public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
                return false;
            }

            @Override
            public boolean onKeyOther(View view, Editable text, KeyEvent event) {
                return false;
            }

            @Override
            public void clearMetaKeyState(View view, Editable content, int states) {

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //跳转到Button演示界面
                Toast.makeText(EditTextActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(HotFixActivity.this,EditTextActivity.class);
                //startActivity(intent);
            }
        });

        Username = findViewById(R.id.et1);
        CharSequence text = Username.getText();
        Spannable span = (Spannable) text;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        span.setSpan(mPinEntryWatcher, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

//        Username.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("xczxcz", charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Username.getText().clear();
//            }
//        });

    }

    private TextWatcher mPinEntryWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence buffer, int start, int olen, int nlen) {
        }

        public void onTextChanged(CharSequence buffer, int start, int olen, int nlen) {
        }

        public void afterTextChanged(Editable buffer) {

                Username.getText().clear();

        }
    };


}