package com.henry.PreferenceTest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.henry.basic.R;


public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    private SharedPreferences sharedPreferences = null;
    private Preference loginDjiAccount;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey);
        //用于取值的SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initView();
    }

    private void initView() {
        loginDjiAccount = findPreference("logout_dji_account");
        if (loginDjiAccount != null) {
            loginDjiAccount.setOnPreferenceClickListener(this);
        }
    }


    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        switch (preference.getKey()) {
            case "logout_dji_account":
                String rtmpUrlStr = sharedPreferences.getString("url_pre", "");
                if ("".equals(rtmpUrlStr)) {
                    Toast.makeText(getActivity(), "请在直播推流地址中随意填写值,再来点我...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "填写了:" + rtmpUrlStr, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return false;
    }

}