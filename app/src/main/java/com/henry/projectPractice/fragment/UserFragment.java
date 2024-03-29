package com.henry.projectPractice.fragment;

import android.view.View;
import android.widget.TextView;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-03-29
 */
public class UserFragment extends BaseFragment {

    private TextView tv;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_user,null);
        tv = (TextView) view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

}
