package com.example.FragmentTest.NavigationBar_Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HomeScreen.R;


public class Navigation_Message_Activity extends AppCompatActivity implements View.OnClickListener {

        //Activity UI Object
        private LinearLayout ly_tab_menu_channel;
        private TextView tab_menu_channel;
        private TextView tab_menu_channel_num;
        private LinearLayout ly_tab_menu_message;
        private TextView tab_menu_message;
        private TextView tab_menu_message_num;
        private LinearLayout ly_tab_menu_better;
        private TextView tab_menu_better;
        private TextView tab_menu_better_num;
        private LinearLayout ly_tab_menu_setting;
        private TextView tab_menu_setting;
        private ImageView tab_menu_setting_partner;
        private FragmentManager fManager;
        private FragmentTransaction fTransaction;
        private MyFragment fg1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_navigation_message);
            bindViews();
            ly_tab_menu_channel.performClick();
            fg1 = new MyFragment();
            fManager = getSupportFragmentManager();
            fTransaction = fManager.beginTransaction();
            fTransaction.add(R.id.ly_content, fg1).commit();
        }


        private void bindViews() {
            ly_tab_menu_channel = (LinearLayout) findViewById(R.id.ly_tab_menu_channel);
            tab_menu_channel = (TextView) findViewById(R.id.tab_menu_channel);
            tab_menu_channel_num = (TextView) findViewById(R.id.tab_menu_channel_num);
            ly_tab_menu_message = (LinearLayout) findViewById(R.id.ly_tab_menu_message);
            tab_menu_message = (TextView) findViewById(R.id.tab_menu_message);
            tab_menu_message_num = (TextView) findViewById(R.id.tab_menu_message_num);
            ly_tab_menu_better = (LinearLayout) findViewById(R.id.ly_tab_menu_better);
            tab_menu_better = (TextView) findViewById(R.id.tab_menu_better);
            tab_menu_better_num = (TextView) findViewById(R.id.tab_menu_better_num);
            ly_tab_menu_setting = (LinearLayout) findViewById(R.id.ly_tab_menu_setting);
            tab_menu_setting = (TextView) findViewById(R.id.tab_menu_setting);
            tab_menu_setting_partner = (ImageView) findViewById(R.id.tab_menu_setting_partner);

            ly_tab_menu_channel.setOnClickListener(this);
            ly_tab_menu_message.setOnClickListener(this);
            ly_tab_menu_better.setOnClickListener(this);
            ly_tab_menu_setting.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ly_tab_menu_channel:
                    setSelected();
                    tab_menu_channel.setSelected(true);
                    tab_menu_channel_num.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ly_tab_menu_message:
                    setSelected();
                    tab_menu_message.setSelected(true);
                    tab_menu_message_num.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ly_tab_menu_better:
                    setSelected();
                    tab_menu_better.setSelected(true);
                    tab_menu_better_num.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ly_tab_menu_setting:
                    setSelected();
                    tab_menu_setting.setSelected(true);
                    tab_menu_setting_partner.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        //重置所有文本的选中状态
        private void setSelected() {
            tab_menu_channel.setSelected(false);
            tab_menu_message.setSelected(false);
            tab_menu_better.setSelected(false);
            tab_menu_setting.setSelected(false);
        }
    }
