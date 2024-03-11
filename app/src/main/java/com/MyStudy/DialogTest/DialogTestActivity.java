package com.MyStudy.DialogTest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.MyStudy.Basic_control_view.R;


import java.util.ArrayList;
import java.util.List;


public class DialogTestActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = "xuejie";
    private int chedkedItem = 0;
    private String name;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);
        bindView();
    }

    private void bindView() {
        Button btn_normal_dialog = (Button) findViewById(R.id.btn_normal_dialog);
        Button btn_item_dialog = (Button) findViewById(R.id.btn_item_dialog);
        Button btn_single_choice = (Button) findViewById(R.id.btn_single_choice);
        Button btn_multi_choice = (Button) findViewById(R.id.btn_multi_choice);
        Button btn_custom_dialog = (Button) findViewById(R.id.btn_custom_dialog);
        Button btn_listview_dialog = (Button) findViewById(R.id.btn_list_dialog);
        Button btn_recycler_dialog = (Button) findViewById(R.id.btn_RecyclerView_dialog);
        btn_normal_dialog.setOnClickListener(this);
        btn_item_dialog.setOnClickListener(this);
        btn_single_choice.setOnClickListener(this);
        btn_multi_choice.setOnClickListener(this);
        btn_custom_dialog.setOnClickListener(this);
        btn_listview_dialog.setOnClickListener(this);
        btn_recycler_dialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal_dialog:
                tipDialog();                //提示对话框
                break;
            case R.id.btn_item_dialog:
                itemListDialog();           //列表对话框
                break;
            case R.id.btn_single_choice:
                singleChoiceDialog();       //单选对话框
                break;
            case R.id.btn_multi_choice:
                multiChoiceDialog();        //多选对话框
                break;
            case R.id.btn_custom_dialog:
                customDialog();             //自定义对话框
                break;
            case R.id.btn_list_dialog:
                ListViewDialog();           //listview对话框
                break;
            case R.id.btn_RecyclerView_dialog:
                RecyclerDialog();           //Recyclerview对话框
                break;
            default:
                break;
        }
    }


    /**
     * 提示对话框
     */
    public void tipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("提示：");
        builder.setMessage("这是一个普通对话框，");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);            //点击对话框以外的区域是否让对话框消失  true 可以消失   false 不会消失(必须做出选择)

        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //设置中立按钮
        builder.setNeutralButton("保密", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你选择了中立", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e(TAG, "对话框显示了");
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e(TAG, "对话框消失了");
            }
        });
        //对话框消失的监听事件
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e(TAG, "对话框取消了");
            }
        });

        dialog.show();                              //显示对话框
    }

    /**
     * - setTitle：设置对话框的标题，比如“提示”、“警告”等；
     * - setMessage：设置对话框要传达的具体信息；
     * - setIcon： 设置对话框的图标；
     * - setCancelable： 点击对话框以外的区域是否让对话框消失，默认为true；
     * - setPositiveButton：设置正面按钮，表示“积极”、“确认”的意思，第一个参数为按钮上显示的文字，下同；
     * - setNegativeButton：设置反面按钮，表示“消极”、“否认”、“取消”的意思；
     * - setNeutralButton：设置中立按钮；
     * - setOnShowListener：对话框显示时触发的事件；
     * - setOnCancelListener：对话框消失时触发的事件。
     * cancel：
     * 回调setOnCancelListener的监听事件—–>执行dismiss。
     * dismiss：
     * 取消dialog—–>回调setOnDismissListener的监听事件。
     */

    /**
     * 列表对话框
     */
    private void itemListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("选择你喜欢的课程：");
        builder.setCancelable(true);
        final String[] lesson = new String[]{"语文", "数学", "英语", "化学", "生物", "物理", "体育"};
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(lesson, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你选择了" + lesson[which], Toast.LENGTH_SHORT).show();
            }
        }).create();
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();     //创建AlertDialog对象
        dialog.show();                              //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("你现在居住地是：");
        final String[] cities = {"北京", "上海", "广州", "深圳", "杭州", "天津", "成都"};
        builder.setSingleChoiceItems(cities, chedkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你选择了" + cities[which], Toast.LENGTH_SHORT).show();
                // chedkedItem = which;
            }
        });

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 复选对话框
     */
    public void multiChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("请选择你喜欢的颜色：");
        final String[] colors = {"红色", "橙色", "黄色", "绿色", "蓝色", "靛色", "紫色"};
        final List<String> myColors = new ArrayList<>();

        builder.setMultiChoiceItems(colors, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    myColors.add(colors[which]);
                } else {
                    myColors.remove(colors[which]);
                }
            }
        });

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = "";
                for (String color : myColors) {
                    result += color + "、";
                }
                Toast.makeText(getApplicationContext(), "你选择了: " + result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myColors.clear();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        dialog.show();                               //显示对话框
    }

    /**
     * 自定义登录对话框
     */
    public void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(DialogTestActivity.this, R.layout.activity_custom, null);
        dialog.setView(dialogView);
        dialog.show();
        final EditText et_name = dialogView.findViewById(R.id.et_name);
        final EditText et_pwd = dialogView.findViewById(R.id.et_pwd);
        final Button btn_login = dialogView.findViewById(R.id.btn_login);
        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                pwd = et_pwd.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(DialogTestActivity.this, "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(DialogTestActivity.this, "用户名：" + name + "\n" + "用户密码：" + pwd, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void ListViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View rootView = inflater.inflate(R.layout.listview, null);
        AlertDialog dialog = builder.create();
        Button confirmBtn = rootView.findViewById(R.id.list_but);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        List<Fruit> fruitlist = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Fruit pineapple = new Fruit(R.drawable.pineapple, "菠萝", "¥16.9 元/KG");
            fruitlist.add(pineapple);
            Fruit mango = new Fruit(R.drawable.mango, "芒果", "¥29.9 元/kg");
            fruitlist.add(mango);
            Fruit pomegranate = new Fruit(R.drawable.pomegranate, "石榴", "¥15元/kg");
            fruitlist.add(pomegranate);
            Fruit grape = new Fruit(R.drawable.grape, "葡萄", "¥19.9 元/kg");
            fruitlist.add(grape);
            Fruit apple = new Fruit(R.drawable.apple, "苹果", "¥20 元/kg");
            fruitlist.add(apple);
            Fruit orange = new Fruit(R.drawable.orange, "橙子", "¥18.8 元/kg");
            fruitlist.add(orange);
            Fruit watermelon = new Fruit(R.drawable.watermelon, "西瓜", "¥28.8元/kg");
            fruitlist.add(watermelon);
        }
        FruitAdapter adapter = new FruitAdapter(this, R.layout.fruit_item, fruitlist);
        ListView listView = rootView.findViewById(R.id.list_view666);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent == listView) {
                    ImageView imageView = view.findViewById(R.id.fruit_image);
                    TextView name = view.findViewById(R.id.fruit_name);
                    TextView price = view.findViewById(R.id.fruit_price);

                    String name2 = name.getText().toString();
                    String price2 = price.getText().toString();
                    Toast.makeText(DialogTestActivity.this, "您选择的水果是：" + name2 + " 价格为" + price2, Toast.LENGTH_SHORT).show();
                }

            }
        });
        listView.setAdapter(adapter);
        dialog.show();
        dialog.getWindow().setContentView(rootView);
    }


    private void RecyclerDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View rootView = inflater.inflate(R.layout.sample_selected_layout, null);
        final AlertDialog dialog = builder.create();
        Button confirmBtn = rootView.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        List<Fruit> fruitlist = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Fruit pineapple = new Fruit(R.drawable.kaka, "卡卡", "8千万");
            fruitlist.add(pineapple);
            Fruit mango = new Fruit(R.drawable.cluo, "C罗", "1亿");
            fruitlist.add(mango);
            Fruit pomegranate = new Fruit(R.drawable.messi, "梅西", "1亿");
            fruitlist.add(pomegranate);
            Fruit grape = new Fruit(R.drawable.gezi, "格列兹曼", "7500w");
            fruitlist.add(grape);
            Fruit apple = new Fruit(R.drawable.bairen, "拜仁全队", "3亿");
            fruitlist.add(apple);
            Fruit orange = new Fruit(R.drawable.jluo, "j罗", "7000w");
            fruitlist.add(orange);
            Fruit watermelon = new Fruit(R.drawable.beikehanmu, "小贝", "8000w");
            fruitlist.add(watermelon);
        }
        RecyclerView recyclerView = rootView.findViewById(R.id.resolution_list_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new DialogRecyclerAdapter(this, fruitlist, new DialogRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onClick(int pos, ImageView view) {
                view=findViewById(fruitlist.get(pos).getImageID());
                Bitmap bitmap0 = ((BitmapDrawable)view.getDrawable()).getBitmap();
                bigImageLoader(bitmap0);
            }
        }));
        dialog.show();
        dialog.getWindow().setContentView(rootView);


    }


    //方法里直接实例化一个imageView不用xml文件，传入bitmap设置图片
    private void bigImageLoader(Bitmap bitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        ImageView image = new ImageView(getApplicationContext());
        image.setImageBitmap(bitmap);
        dialog.setContentView(image);
        //将dialog周围的白块设置为透明
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //显示
        dialog.show();
        //点击图片取消
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }




}