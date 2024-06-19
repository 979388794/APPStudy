package com.henry.diagnosisTest.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.henry.basic.R;


/**
 * DatabindingAdapter 控制UI
 */
public class DataBandingAdapter {

    @BindingAdapter("status_code")
    public static void setItemStatus(ImageView view, int statusCode) {
        view.setVisibility(statusCode == -1 ? View.GONE : View.VISIBLE);
        view.setImageResource(
                statusCode != 0 ? R.mipmap.icon_warning : R.mipmap.icon_success);

    }


    @BindingAdapter("module_status_code")
    public static void setItemModuleStatus(ImageView view, int moduleStatusCode) {
        switch (moduleStatusCode) {
            case 0:
                view.setImageResource(R.mipmap.icon_success);
                break;
            case 1:
                view.setImageResource(R.mipmap.icon_warning);
                break;
            case 2:
                view.setImageResource(R.mipmap.icon_fail);
                break;
        }

    }


}
