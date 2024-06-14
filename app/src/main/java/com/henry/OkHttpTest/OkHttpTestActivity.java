package com.henry.OkHttpTest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.henry.basic.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpTestActivity extends AppCompatActivity implements View.OnClickListener {
    Button post;
    Button get;
    TextView Response;
    String TAG = "Henry";
    private static final MediaType CONTENT_TYPE = MediaType.get("application/x-www-form-urlencoded");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);
        bindview();
        Log.e(TAG, "Activity.class 由：" + Activity.class.getClassLoader() + " 加载");
        Log.e(TAG, "HotFixActivity.class 由：" + getClassLoader() + " 加载");
        //synchronous_request();
    }

    void bindview() {
        post = findViewById(R.id.post);
        get = findViewById(R.id.get);
        Response = findViewById(R.id.Response);
        post.setOnClickListener(this);
        get.setOnClickListener(this);
    }

    public void getAsync() {
        //异步请求
        OkHttpClient httpClient = new OkHttpClient();
        String url = "https://www.baidu.com/";
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        //准备好请求的Call对象
        Call call = httpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "okHttpGet enqueue: onResponse:" + response.body().string());
                ResponseBody body = response.body();
                String string = body.string();
                byte[] bytes = body.bytes();
                InputStream inputStream = body.byteStream();
            }
        });
    }

    private void getOkhttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new MyCallBack());
                    Response response = client.newCall(request).execute();
                    String Data = response.body().string();
                    Log.d(TAG, "  " + Data);
                    showData(Data);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "exception");
                }
            }
        }).start();
    }

    private void postOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("zfk", "666666")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")
                            .method("POST", requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    String Data = response.body().string();
                    showData(Data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showData(final String Data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //
                Response.setText(Data);
            }
        });
    }

    void get() {
        OkHttpClient httpClient = new OkHttpClient();

        String url = "https://www.baidu.com/";
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = httpClient.newCall(getRequest);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步请求，要放到子线程执行
                    Response response = call.execute();
                    Log.i(TAG, "okHttpGet run: response:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    void post() {
        OkHttpClient httpClient = new OkHttpClient();
        MediaType contentType = MediaType.parse("text/x-markdown; charset=utf-8");//发送String
        String content = "hello!";
        RequestBody body = RequestBody.create(contentType, content);

        // RequestBody:jsonBody，json字符串
        String json = "jsonString";
        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        //RequestBody:fileBody, 上传文件
        File file = new File(Environment.getExternalStorageDirectory(), "1.png");
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);

        //post请求提交表单
        //RequestBody:FormBody，表单键值对
        RequestBody formBody = new FormBody.Builder()
                .add("username", "hfy")
                .add("password", "qaz")
                .build();

        Request getRequest = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(body)
                .build();

        Call call = httpClient.newCall(getRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "okHttpPost enqueue: \n onResponse:" + response.toString() + "\n body:" + response.body().string());
            }
        });
    }

    void post_complex() {
        /**
         * post请求提交复杂请求体
         */
        OkHttpClient httpClient = new OkHttpClient();
//        MediaType contentType = MediaType.parse("text/x-markdown; charset=utf-8");
//        String content = "hello!";
//        RequestBody body = RequestBody.create(contentType, content);

        //RequestBody:fileBody,上传文件
        /*
        File file = drawableToFile(this, R.mipmap.longzhu, new File("00.jpg"));
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        //RequestBody:multipartBody, 多类型 （用户名、密码、头像）
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "hufeiyang")
                .addFormDataPart("phone", "123456")
                .addFormDataPart("touxiang", "00.png", fileBody)
                .build();

        Request getRequest = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(multipartBody)
                .build();

        Call call = httpClient.newCall(getRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.i(TAG, "okHttpPost enqueue: \n onFailure:"+ call.request().toString() +"\n body:" +call.request().body().contentType()
                        +"\n IOException:"+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "okHttpPost enqueue: \n onResponse:"+ response.toString() +"\n body:" +response.body().string());
            }
        });*/
    }


    class MyCallBack implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, okhttp3.Response response) throws IOException {
            showData(response.body().string());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                getOkhttp();
                get();
                Log.i(TAG, "get..............");
                break;
            case R.id.post:
                postOkhttp();
                Log.i(TAG, "post..............");
                break;
            default:
                break;
        }
    }


}