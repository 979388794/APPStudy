package com.henry.HotFixTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.henry.basic.R;

public class HotFixActivity2 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotfix);
        Button merge=findViewById(R.id.fix);
        Button bug=findViewById(R.id.bug);
        merge.setOnClickListener(this);
        bug.setOnClickListener(this);
    }

    /**
     * 关于dex文件被恶意加载和替换的解决方案
     * 1.可通过在服务器生成一个dex文件的MD5列表，在修复之前客户端
     * 向服务发送验证请求,验证通过即可修复。
     * 2.将dex文件打包为rar并且设置密码，在客户端通过ndk进行验证解密
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fix:
                HotFixEngine.copyDexFileToAppAndFix(this,"classes_fix.dex",true);
                break;
            case R.id.bug:
                new TestClass().showToast(null,getApplication());
                break;
        }
    }
}