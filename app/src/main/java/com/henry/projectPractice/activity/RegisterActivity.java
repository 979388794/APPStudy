package com.henry.projectPractice.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.henry.projectPractice.bean.ResponseBean;
import com.henry.projectPractice.utils.APIConfig;
import com.henry.projectPractice.bean.ResultBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    String TAG = "Henry";
    private EditText et_name;
    private EditText et_email;
    private EditText et_phonenumber;
    private EditText et_password;
    private EditText et_password2;
    private Button btn_register;
    private Button btn_login;
    private Button sendmessage;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String result = msg.obj.toString();
                Gson gson = new Gson();
                ResponseBean loginBean = gson.fromJson(result, ResponseBean.class);
                int successCode = loginBean.getCode();
                if (successCode == 200) {
                    Toast.makeText(getApplicationContext(), "注册成功!" + "\n立马去登陆", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(getApplicationContext(), "邮箱/验证码不正确！", Toast.LENGTH_SHORT).show();
                }
            }
            if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "邮箱验证码发送成功", Toast.LENGTH_SHORT).show();
                sendmessage.setEnabled(false);
                countdown();
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
        et_email = (EditText) findViewById(R.id.et_email);
        et_phonenumber = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        sendmessage = (Button) findViewById(R.id.send_message);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        sendmessage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                Register();
                break;
            case R.id.btn_login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.send_message:
                if (isValidEmail()) {
                    sendVerificationCode();
                    sendmessage.setEnabled(false);
                    countdown();
                } else {
                    Toast.makeText(getApplicationContext(), "邮箱格式不正确，请输入正确的邮箱！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean isValidEmail() {
        String email = et_email.getText().toString().trim();
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void sendVerificationCode() {
        String email = et_email.getText().toString().trim();
        OkHttpClient client = new OkHttpClient();
        JSONObject object = new JSONObject();
        try {
            object.put("mail", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, object.toString());
        Request request = new Request.Builder().url(APIConfig.BASE_URL_EMAIL).post(requestBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "验证码发送失败，请稍后重试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i(TAG, "Successful! " + result);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void Register() {
        String name = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入邮箱!", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = et_phonenumber.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入您的邮箱验证码!", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        String password2 = et_password2.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "请确认密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(password2)) {
            Toast.makeText(this, "两次密码不一致，请重新输入!", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpClient client = new OkHttpClient();
        JSONObject object = new JSONObject();
        try {
            object.put("account", name);
            object.put("code", phone);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, object.toString());
        Request request = new Request.Builder().url(APIConfig.BASE_URL_REGISTER).post(requestBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "请检查网络或稍后重试！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i(TAG, "Successful! " + result);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void countdown() {
        MyCountDownTimer mCountDownTimer = new MyCountDownTimer(60000 + 400, 1000);
        mCountDownTimer.start();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            sendmessage.setText("发送验证码");
            sendmessage.setEnabled(true);
            sendmessage.setBackgroundResource(R.drawable.login_btn_bg);
        }

        public void onTick(long millisUntilFinished) {
            sendmessage.setText(millisUntilFinished / 1000 + "s");
        }
    }
}