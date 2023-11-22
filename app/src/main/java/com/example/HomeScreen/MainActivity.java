package com.example.HomeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.HomeScreen.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String Tag = "xuejie";
    String ActivityIntent;

    List<ActivityFragment> Fragmentlist= new ArrayList<ActivityFragment>();

    private ActivityFragment fg[];
    private ActivityFragment fg1, fg2, fg3, fg4,fg5,fg6,fg7,fg8,fg9,fg10,fg11,fg12,fg13,fg14,fg15,fg16,fg17,fg18,fg19,fg20,fg21;
    private FragmentManager fManager;
    private static final String[] REQUEST_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fManager = getSupportFragmentManager();
//        for(int i=0;i<20;i++){
//            Fragmentlist.add(fg[i]);
//        }
        setlisteners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasPermissionsGranted(REQUEST_PERMISSIONS)) {
            Log.d(Tag, "-------activityCompat.requestPermissions--------");
            ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        Log.d(Tag, "-------onResume--------");
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(Tag, "-------onRequestPermissionsResult--------");
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!hasPermissionsGranted(REQUEST_PERMISSIONS)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * ContextCompat类的checkSelfPermission方法用于检测用户是否授权了某个权限。
     * PackageManager.PERMISSION_DENIED ： -1
     * PackageManager.PERMISSION_GRANTED ： 0 表示授权成功
     */
    public boolean hasPermissionsGranted(@NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
                //此时没有授权，返回false
            }
        }
        return true;
        //已经授权过，返回true
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
         * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
         * if it is present.
         */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /**
         * 在onCreateOptionsMenu执行后，菜单被显示前调用；如果菜单已经被创建，则在菜单显示前被调用。 同样的，
         * 返回true则显示该menu,false 则不显示; （可以通过此方法动态的改变菜单的状态，比如加载不同的菜单等） TODO
         * Auto-generated method stub
         */
        return true;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        /**
         * 每次菜单被关闭时调用. （菜单被关闭有三种情形，menu按钮被再次点击、back按钮被点击或者用户选择了某一个菜单项） TODO
         * Auto-generated method stub
         */
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。 TODO Auto-generated
         * method stub
         */
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_setting) {
            showSampleDialog();
        }
        return true;
    }

    private void showSampleDialog() {

    }

    private void setlisteners() {
        OnClick onClick = new OnClick();
        binding.textView1.setOnClickListener(onClick);
        binding.editText.setOnClickListener(onClick);
        binding.radioButton.setOnClickListener(onClick);
        binding.checkbox.setOnClickListener(onClick);
        binding.imageview.setOnClickListener(onClick);
        binding.LineChart.setOnClickListener(onClick);
        binding.LineChart2.setOnClickListener(onClick);
        binding.CustomView.setOnClickListener(onClick);
        binding.CustomViewGroup.setOnClickListener(onClick);
        binding.ViewModel.setOnClickListener(onClick);
        binding.LiveData.setOnClickListener(onClick);
        binding.PowerControl.setOnClickListener(onClick);
        binding.ViewTreeObserver.setOnClickListener(onClick);
        binding.SensorManagerTest.setOnClickListener(onClick);
        binding.DialogTest.setOnClickListener(onClick);
        binding.listRecyclerView.setOnClickListener(onClick);
        binding.PreferenceTest.setOnClickListener(onClick);
        binding.SystemServiceTest.setOnClickListener(onClick);
        binding.WindowManagerTest.setOnClickListener(onClick);
        binding.Fragment.setOnClickListener(onClick);
        binding.DisplayAdapter.setOnClickListener(onClick);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);

    }


    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentTransaction fTransaction = fManager.beginTransaction();

            hideAllFragment(fTransaction);
            switch (v.getId()) {
                case R.id.checkbox:
                    ActivityIntent="checkbox";
                    if (fg1 == null) {
                        fg1 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg1);
                    } else {
                        fTransaction.show(fg1);
                    }
                    break;
                case R.id.text_view1:
                    ActivityIntent="Textview";
                    if (fg2 == null) {
                        fg2 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg2);
                    } else {
                        fTransaction.show(fg2);
                    }
                    break;
                case R.id.editText:
                    ActivityIntent="editText";
                    if (fg3 == null) {
                        fg3 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg3);
                    } else {
                        fTransaction.show(fg3);
                    }
                    break;
                case R.id.radioButton:
                    ActivityIntent="radioButton";
                    if (fg4 == null) {
                        fg4 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg4);
                    } else {
                        fTransaction.show(fg4);
                    }
                    break;

                case R.id.imageview:
                    ActivityIntent="imageview";
                    if (fg5 == null) {
                        fg5 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg5);
                    } else {
                        fTransaction.show(fg5);
                    }
                    break;

                case R.id.LineChart:
                    ActivityIntent="LineChart";
                    if (fg6 == null) {
                        fg6 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg6);
                    } else {
                        fTransaction.show(fg6);
                    }
                    break;
                case R.id.LineChart2:
                    ActivityIntent="LineChart2";
                    if (fg7 == null) {
                        fg7 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg7);
                    } else {
                        fTransaction.show(fg7);
                    }
                    break;
                case R.id.CustomView:
                    ActivityIntent="CustomView";
                    if (fg8 == null) {
                        fg8 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg8);
                    } else {
                        fTransaction.show(fg8);
                    }
                    break;
                case R.id.CustomViewGroup:
                    ActivityIntent="CustomViewGroup";
                    if (fg9 == null) {
                        fg9 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg9);
                    } else {
                        fTransaction.show(fg9);
                    }
                    break;
                case R.id.ViewModel:
                    ActivityIntent="ViewModel";
                    if (fg10 == null) {
                        fg10 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg10);
                    } else {
                        fTransaction.show(fg10);
                    }
                    break;
                case R.id.LiveData:
                    ActivityIntent="LiveData";
                    if (fg11 == null) {
                        fg11 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg11);
                    } else {
                        fTransaction.show(fg11);
                    }
                    break;
                case R.id.PowerControl:
                    ActivityIntent="PowerControl";
                    if (fg12 == null) {
                        fg12 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg12);
                    } else {
                        fTransaction.show(fg12);
                    }
                    break;
                case R.id.ViewTreeObserver:
                    ActivityIntent="ViewTreeObserver";
                    if (fg13 == null) {
                        fg13 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg13);
                    } else {
                        fTransaction.show(fg13);
                    }
                    break;
                case R.id.SensorManagerTest:
                    ActivityIntent="SensorManagerTest";
                    if (fg14 == null) {
                        fg14 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg14);
                    } else {
                        fTransaction.show(fg14);
                    }
                    break;
                case R.id.DialogTest:
                    ActivityIntent="DialogTest";
                    if (fg15 == null) {
                        fg15 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg15);
                    } else {
                        fTransaction.show(fg15);
                    }
                    break;
                case R.id.list_recycler_view:
                    ActivityIntent="list_recycler_view";
                    if (fg16 == null) {
                        fg16 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg16);
                    } else {
                        fTransaction.show(fg16);
                    }
                    break;
                case R.id.Preference_Test:
                    ActivityIntent="Preference_Test";
                    if (fg17 == null) {
                        fg17 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg17);
                    } else {
                        fTransaction.show(fg17);
                    }
                    break;
                case R.id.System_service_Test:
                    ActivityIntent="System_service_Test";
                    if (fg18 == null) {
                        fg18 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg18);
                    } else {
                        fTransaction.show(fg18);
                    }
                    break;
                case R.id.WindowManager_Test:
                    ActivityIntent="WindowManager_Test";
                    if (fg19 == null) {
                        fg19 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg19);
                    } else {
                        fTransaction.show(fg19);
                    }
                    break;
                case R.id.Fragment:
                    ActivityIntent="Fragment";
                    if (fg20 == null) {
                        fg20 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg20);
                    } else {
                        fTransaction.show(fg20);
                    }
                    break;
                case R.id.Display_Adapter:
                    ActivityIntent="Display_Adapter";
                    if (fg21 == null) {
                        fg21 = new ActivityFragment(ActivityIntent,MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg21);
                    } else {
                        fTransaction.show(fg21);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
            fTransaction.commit();
        }
    }



}