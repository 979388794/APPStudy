package com.henry.PreferenceTest;

import androidx.annotation.NonNull;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.henry.basic.R;


import androidx.preference.Preference;
import androidx.preference.PreferenceManager;


public class Test_Settings_Activity extends PreferenceActivity implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    SharedPreferences prefs;
    android.preference.Preference checkbox;
    android.preference.Preference editbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.test_settings);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        checkbox = findPreference("checkbox");
        checkbox.setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.preference.Preference preference) {
                Toast.makeText(Test_Settings_Activity.this, "xuejie", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        checkbox.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object o) {
                return true;
            }
        });
        editbox = findPreference("edit2");
        editbox.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                return true;
            }
        });
    }
//    private void init() {
//        preference = (ListPreference) findPreference("key_listpreference");
//        ///preference.setOnPreferenceClickListener(this); ****点击事件已经被覆盖掉了****
//        preference.setOnPreferenceChangeListener(this);
//        // 设置summary为所选中的值列表值
//        if (preference.getEntry() != null) {
//            preference.setSummary(preference.getEntry());//初始化时设置summary
//        }
//    }
    /**
     * 那么这三个点击事件在一起是什么用的呢?它的规则如下：
     *
     * 点击某个Preference控件后，会先回调onPreferenceChange()方法，即是否保存值（这个下面会讲解这个值是如何保存的），
     * 然后再回调onPreferenceClick以及onPreferenceTreeClick()方法，
     * 因此在onPreferenceClick/onPreferenceTreeClick方法中我们得到的控件值就是最新的Preference控件值。
     * 然后onPreferenceClick会比onPreferenceTreeClick()方法先调用，如果onPreferenceClick方法返回true,
     * 那就不会再调用onPreferenceTreeClick()方法，
     * 如果onPreferenceClick()返回false就会继续调用onPreferenceTreeClick()方法
     */

    /**
     * 当点击控件时触发发生，可以做相应操作
     *
     * @param preference
     * @return
     */
    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        return true;
    }

    /**
     * 监听点击事件
     */
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, android.preference.Preference preference) {
        if (preference.getKey().equals("checkbox")) {
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    /**
     * 当Preference的元素值发送改变时，触发该事件,记住只有元素值改变才会触发这个值，
     * 返回值为true 表示将新值写入sharedPreference文件中
     * false 则不将新值写入sharedPreference文件
     *
     * @param preference
     * @param newValue
     * @return
     */
    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
        if (preference.getKey().equals("edit2")) {
        }
        return true;
    }
}