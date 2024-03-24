package com.henry.basic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.henry.basic.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String Tag = "xuejie";
    String ActivityIntent;


    private ActivityFragment fg1, fg2, fg3, fg4, fg5, fg6, fg7, fg8, fg9, fg10, fg11, fg12, fg13, fg14, fg15, fg16, fg17, fg18, fg19, fg20, fg21, fg22;
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
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
//        test();
//        testJackson();
        setlisteners();
    }
    public static class User {
        private String name;
        private int age;

        public User() {
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }


        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }


    }

//    public static void testJackson() {
//        try {
//            // 创建 ObjectMapper 对象
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // 创建一个 User 对象
//            User user = new User("Bob", 30);
//
//            // 将 User 对象转换为 JSON 字符串
//            String json = objectMapper.writeValueAsString(user);
//            Log.d("JacksonTest", "Serialized JSON: " + json);
//
//            // 将 JSON 字符串转换为 User 对象
//            User deserializedUser = objectMapper.readValue(json, User.class);
//            Log.d("JacksonTest", "Deserialized User: " + deserializedUser.getName() + ", " + deserializedUser.getAge());
//        } catch (Exception e) {
//            Log.e("JacksonTest", "Error during Jackson test: " + e.getMessage());
//        }
//    }
//    void test() {
//        try {
//            // 构建一个 JSON 对象
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name", "Alice");
//            jsonObject.put("age", 25);
//            Log.d("henry", "" + jsonObject);
//
//            // 构建一个 JSON 数组
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put("apple");
//            jsonArray.put("banana");
//            jsonArray.put("cherry");
//            Log.d("henry", "" + jsonArray);
//
//            // 将 JSON 对象和 JSON 数组组合成一个新的 JSON 对象
//            JSONObject mainObject = new JSONObject();
//            mainObject.put("person", jsonObject);
//            mainObject.put("fruits", jsonArray);
//            Log.d("henry", "" + mainObject);
//
//            // 将 JSON 对象转换为字符串输出
//            String jsonString = mainObject.toString();
//            Log.d("henry", "" + jsonString);
//
//            // 解析 JSON 字符串
//            JSONObject parsedObject = new JSONObject(jsonString);
//            Log.d("henry", "" + parsedObject.get("person"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!hasPermissionsGranted(REQUEST_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        binding.OpenGl.setOnClickListener(onClick);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
        if (fg5 != null) fragmentTransaction.hide(fg5);
        if (fg6 != null) fragmentTransaction.hide(fg6);
        if (fg7 != null) fragmentTransaction.hide(fg7);
        if (fg8 != null) fragmentTransaction.hide(fg8);
        if (fg9 != null) fragmentTransaction.hide(fg9);
        if (fg10 != null) fragmentTransaction.hide(fg10);
        if (fg11 != null) fragmentTransaction.hide(fg11);
        if (fg12 != null) fragmentTransaction.hide(fg12);
        if (fg13 != null) fragmentTransaction.hide(fg13);
        if (fg14 != null) fragmentTransaction.hide(fg14);
        if (fg15 != null) fragmentTransaction.hide(fg15);
        if (fg16 != null) fragmentTransaction.hide(fg16);
        if (fg17 != null) fragmentTransaction.hide(fg17);
        if (fg18 != null) fragmentTransaction.hide(fg18);
        if (fg19 != null) fragmentTransaction.hide(fg19);
        if (fg20 != null) fragmentTransaction.hide(fg20);
        if (fg21 != null) fragmentTransaction.hide(fg21);
        if (fg22 != null) fragmentTransaction.hide(fg22);
    }


    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentTransaction fTransaction = fManager.beginTransaction();

            hideAllFragment(fTransaction);
            switch (v.getId()) {
                case R.id.text_view1:
                    ActivityIntent = "Textview";
                    if (fg2 == null) {
                        fg2 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg2);
                    } else {
                        fTransaction.show(fg2);
                    }
                    break;
                case R.id.editText:
                    ActivityIntent = "editText";
                    if (fg3 == null) {
                        fg3 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg3);
                    } else {
                        fTransaction.show(fg3);
                    }
                    break;
                case R.id.radioButton:
                    ActivityIntent = "radioButton";
                    if (fg4 == null) {
                        fg4 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg4);
                    } else {
                        fTransaction.show(fg4);
                    }
                    break;

                case R.id.imageview:
                    ActivityIntent = "imageview";
                    if (fg5 == null) {
                        fg5 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg5);
                    } else {
                        fTransaction.show(fg5);
                    }
                    break;

                case R.id.LineChart:
                    ActivityIntent = "LineChart";
                    if (fg6 == null) {
                        fg6 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg6);
                    } else {
                        fTransaction.show(fg6);
                    }
                    break;
                case R.id.LineChart2:
                    ActivityIntent = "LineChart2";
                    if (fg7 == null) {
                        fg7 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg7);
                    } else {
                        fTransaction.show(fg7);
                    }
                    break;
                case R.id.CustomView:
                    ActivityIntent = "CustomView";
                    if (fg8 == null) {
                        fg8 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg8);
                    } else {
                        fTransaction.show(fg8);
                    }
                    break;
                case R.id.CustomViewGroup:
                    ActivityIntent = "CustomViewGroup";
                    if (fg9 == null) {
                        fg9 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg9);
                    } else {
                        fTransaction.show(fg9);
                    }
                    break;
                case R.id.ViewModel:
                    ActivityIntent = "ViewModel";
                    if (fg10 == null) {
                        fg10 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg10);
                    } else {
                        fTransaction.show(fg10);
                    }
                    break;
                case R.id.LiveData:
                    ActivityIntent = "LiveData";
                    if (fg11 == null) {
                        fg11 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg11);
                    } else {
                        fTransaction.show(fg11);
                    }
                    break;
                case R.id.PowerControl:
                    ActivityIntent = "PowerControl";
                    if (fg12 == null) {
                        fg12 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg12);
                    } else {
                        fTransaction.show(fg12);
                    }
                    break;
                case R.id.ViewTreeObserver:
                    ActivityIntent = "ViewTreeObserver";
                    if (fg13 == null) {
                        fg13 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg13);
                    } else {
                        fTransaction.show(fg13);
                    }
                    break;
                case R.id.SensorManagerTest:
                    ActivityIntent = "SensorManagerTest";
                    if (fg14 == null) {
                        fg14 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg14);
                    } else {
                        fTransaction.show(fg14);
                    }
                    break;
                case R.id.DialogTest:
                    ActivityIntent = "DialogTest";
                    if (fg15 == null) {
                        fg15 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg15);
                    } else {
                        fTransaction.show(fg15);
                    }
                    break;
                case R.id.list_recycler_view:
                    ActivityIntent = "list_recycler_view";
                    if (fg16 == null) {
                        fg16 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg16);
                    } else {
                        fTransaction.show(fg16);
                    }
                    break;
                case R.id.Preference_Test:
                    ActivityIntent = "Preference_Test";
                    if (fg17 == null) {
                        fg17 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg17);
                    } else {
                        fTransaction.show(fg17);
                    }
                    break;
                case R.id.System_service_Test:
                    ActivityIntent = "System_service_Test";
                    if (fg18 == null) {
                        fg18 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg18);
                    } else {
                        fTransaction.show(fg18);
                    }
                    break;
                case R.id.WindowManager_Test:
                    ActivityIntent = "WindowManager_Test";
                    if (fg19 == null) {
                        fg19 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg19);
                    } else {
                        fTransaction.show(fg19);
                    }
                    break;
                case R.id.Fragment:
                    ActivityIntent = "Fragment";
                    if (fg20 == null) {
                        fg20 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg20);
                    } else {
                        fTransaction.show(fg20);
                    }
                    break;
                case R.id.Display_Adapter:
                    ActivityIntent = "Display_Adapter";
                    if (fg21 == null) {
                        fg21 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg21);
                    } else {
                        fTransaction.show(fg21);
                    }
                    break;
                case R.id.OpenGl:
                    ActivityIntent = "OpenGl_Test";
                    if (fg22 == null) {
                        fg22 = new ActivityFragment(ActivityIntent, MainActivity.this);
                        fTransaction.add(R.id.ActivityContent, fg22);
                    } else {
                        fTransaction.show(fg22);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
            fTransaction.commit();
        }
    }


}