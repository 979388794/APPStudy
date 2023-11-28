package com.example.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.DialogTest.DialogTestActivity;
import com.example.PowerControlTest.PowerContronlActivity;
import com.example.PreferenceTest.PreferenceActivity;
import com.example.SensorManagerTest.SensorManagerActivity;
import com.example.ViewModel.TimerActivity;
import com.example.ViewModel.View_Data_Activity;
import com.example.ViewTreeObserverTest.ViewTreeObserverActivity;
import com.example.aidlTest.System_Event_Test;
import com.example.custom_view.Custom_view_Activity;
import com.example.custom_view.Custom_view_group;
import com.example.linechart.More_Line_Chart;
import com.example.list_recycle_view.List_Recycler_ViewTestActivity;
import com.example.windowManagerTest.windowManagerActivity;
import com.example.OpenGl.SurfaceViewActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class ActivityFragment extends Fragment {

    private String content;
    Button button;

    String a;
    Context context;
    Onclick onclick;
    static Map<String, Class> mMap = new HashMap<String, Class>() {{
        put("Textview", TextViewActivity.class);
        put("checkbox", CheckBoxActivity.class);
        put("editText", EditTextActivity.class);
        put("radioButton", RadioButtonActivity.class);
        put("imageview", ImageviewActivity.class);
        put("LineChart", LineChartActivity.class);
        put("LineChart2", More_Line_Chart.class);
        put("CustomView", Custom_view_Activity.class);
        put("ViewModel", TimerActivity.class);
        put("CustomViewGroup", Custom_view_group.class);
        put("LiveData", View_Data_Activity.class);
        put("PowerControl", PowerContronlActivity.class);
        put("ViewTreeObserver", ViewTreeObserverActivity.class);
        put("SensorManagerTest", SensorManagerActivity.class);
        put("DialogTest", DialogTestActivity.class);
        put("list_recycler_view", List_Recycler_ViewTestActivity.class);
        put("Preference_Test", PreferenceActivity.class);
        put("System_service_Test", System_Event_Test.class);
        put("WindowManager_Test", windowManagerActivity.class);
        put("Fragment", FragmentActivity.class);
        put("Display_Adapter", TestActivity.class);
        put("OpenGl_Test", SurfaceViewActivity.class);
    }};

    public ActivityFragment(String content1, Context context1) {
        this.content = content1;
        context = context1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(content);
        button = view.findViewById(R.id.activity_But);
        button.setOnClickListener(onclick);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onclick = new Onclick();

    }

    public class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Class<?> activityClass = mMap.get(content);
            Log.d("xuejie",  activityClass+ " ");
            Intent intent = new Intent(context, activityClass);
            startActivity(intent);
        }
    }

}
