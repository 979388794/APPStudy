package com.henry.projectPractice.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.henry.basic.R;
import com.henry.projectPractice.utils.APIConfig;
import com.henry.projectPractice.bean.ResultBean;

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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_nicke;
    private EditText et_phonr;
    private EditText et_password;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private RadioGroup rg_sex;
    private Button btn_register;
    private Button btn_login;

    private OkHttpClient client = new OkHttpClient();
    private Intent intent = null;

    String sex = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String resultStr = (String) msg.obj;
                Log.i("获取返回的信息：", resultStr);
                final ResultBean resultBean = new Gson().fromJson(resultStr, ResultBean.class);
                int resultCode = resultBean.getCode();
                if (resultCode == 200) {
                    Toast.makeText(getApplicationContext(), "注册成功：" + resultStr, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "注册失败：" + resultStr, Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_nicke = (EditText) findViewById(R.id.et_nicke);
        et_phonr = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb_man.getId()) {
                    sex = "1";
                } else {
                    sex = "0";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                Register();
                break;
            case R.id.btn_login:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void Register() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String nicke = et_nicke.getText().toString().trim();
        if (TextUtils.isEmpty(nicke)) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = et_phonr.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入你的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", name);
            jsonObject.put("nickName", nicke);
            jsonObject.put("phonenumber", phone);
            jsonObject.put("sex", sex);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        final RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(APIConfig.URL_BASE + "/user/register")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }


}
