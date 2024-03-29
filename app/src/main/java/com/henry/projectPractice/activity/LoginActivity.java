package com.henry.projectPractice.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.gson.Gson;
import com.henry.basic.R;
import com.henry.projectPractice.bean.LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author: henry.xue
 * @date: 2024-03-29
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_name;
    private EditText edt_psw;
    private Button btn_login;
    private Button btn_register;
    private String token;
    private int code;
    private Intent intent = null;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg==obtainMessage()) {
                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_psw = (EditText) findViewById(R.id.edt_psw);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.btn_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void Login() {
        final String username =  edt_name.getText().toString().trim();
        final String password = edt_psw.getText().toString().trim();
        if (TextUtils.isEmpty(username )) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (true) {
            OkHttpClient client = new OkHttpClient();
            final JSONObject jsonObject = new JSONObject();

            try {//提交的参数
                jsonObject.put("username",username);
                jsonObject.put("password",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType mMediaType = MediaType.parse("application/json; charset=utf-8");
            final RequestBody requestBody = RequestBody.create(mMediaType, jsonObject.toString());
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url("http://192.168.196.45:8080/login")
                    .build();
            okhttp3.Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //请求失败
                    Log.i("请求情况：", "请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.i("响应状态", "响应成功");
                        final String loginBody = response.body().string();
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(loginBody, LoginBean.class);
                        String loginResultCode = loginBean.getCode();
                        Log.i("返回状态码", loginResultCode);
                        //响应成功,判断状态码
                        if (loginResultCode.equals("200")) {
                            Log.i("登录状态", "登录成功");
                            //获取token
                            token = loginBean.getToken();
                            // 把token保存到本地
                            SharedPreferences.Editor editor= getSharedPreferences("get_token", MODE_PRIVATE).edit();
                            editor.putString("token",token);
                            editor.putString("username",username);
                            editor.putString("password",password);
                            editor.apply();

                            //保存token
                            //登录成功，跳到主界面
                            Message message = handler.obtainMessage();
                            message.obj = token;
                            handler.sendMessage(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),loginBody,Toast.LENGTH_LONG).show();
                                }
                            });
                            startActivity(new Intent(LoginActivity.this, myActivity.class));
                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                }

            });
        }
    }


}


