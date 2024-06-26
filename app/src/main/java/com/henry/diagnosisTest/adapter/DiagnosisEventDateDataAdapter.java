package com.henry.diagnosisTest.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.henry.basic.R;
import com.henry.diagnosisTest.listener.OnEventsItemViewClickListener;
import com.henry.diagnosisTest.listener.OnUIChangeListener;
import com.henry.diagnosisTest.model.DiagnosisEvent;
import com.henry.diagnosisTest.model.DiagnosisEventsItem;
import com.quectel.communication.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagnosisEventDateDataAdapter extends RecyclerView.Adapter {


    private OnEventsItemViewClickListener mOnEventsItemViewClickListener;
    private OnUIChangeListener mOnUIChangeListener;

    private final static int VIEW_PARENT = R.layout.item_parent;//父级布局
    private final static int VIEW_CHILD = R.layout.item_child;//子级布局

    //定义一个Object类型的集合来存放混合了day和item的数据实体
    private ArrayList<Object> dayDataList = new ArrayList<>();
    //day num 列表数据,其中days最大值7
    private ArrayList<DiagnosisEvent> dayNumList;
    private final String TAG = getClass().getSimpleName();

    public DiagnosisEventDateDataAdapter(ArrayList<DiagnosisEvent> eventsRootList) {
        this.dayNumList = eventsRootList;
    }

    public void setEventsRootData(ArrayList<DiagnosisEvent> list) {
        if (null != dayNumList) {
            dayNumList.clear();
            dayNumList.addAll(list);
        } else {
            dayNumList = list;
        }
    }

    public void setOnViewClickListener(OnEventsItemViewClickListener listener) {
        this.mOnEventsItemViewClickListener = listener;
    }

    public void setOnUIChangeListener(EventExpandenbleItemDecoration expandenbleItemDecoration) {
        this.mOnUIChangeListener = expandenbleItemDecoration;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == VIEW_PARENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(VIEW_PARENT, parent, false);
            holder = new ParentHolder(view);
        } else if (viewType == VIEW_CHILD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(VIEW_CHILD, parent, false);
            holder = new ChildHolder(view);
        }
        return holder;
    }

    private String convertDay(String str) {
        if (str.length() == 8)
            str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        return str;
    }

    private String convertTime(String str) {
        if (str.length() == 6)
            str = str.substring(0, 2) + ":" + str.substring(2, 4) + ":" + str.substring(4, 6);
        return str;
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof ParentHolder) {
            ParentHolder parentHolder = (ParentHolder) holder;
            DiagnosisEvent historyDateData = (DiagnosisEvent) dayDataList.get(position);
            parentHolder.textView.setText(convertDay(historyDateData.getDate()));
            //测试日期item点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //更新展开折叠状态，如果当前是折叠，则变更为展开，如果当前是展开，则变成折叠
                    Log.d(TAG, "onClick: Dia parentHolder position " + position);
                    ((DiagnosisEvent) dayDataList.get(position)).setExpand(!((DiagnosisEvent) dayDataList.get(position)).isExpand());
                    //刷新列表UI
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof ChildHolder) {
            ChildHolder childHolder = (ChildHolder) holder;
            final DiagnosisEventsItem dateData = (DiagnosisEventsItem) dayDataList.get(position);
            childHolder.textViewTime.setText("time:" + convertTime(dateData.getTime()));
            childHolder.textViewDiagIndex.setText("diag_index:" + dateData.getDiag_index());
            childHolder.textViewDiagLen.setText("diag_len:" + dateData.getDiag_len());
            childHolder.textViewNetState.setText("net_state:" + dateData.getNet_state());
            childHolder.textViewReason.setText("reason:" + dateData.getReason());
            childHolder.imageView.setImageResource(R.mipmap.icon_warning);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnEventsItemViewClickListener != null) {
                        mOnEventsItemViewClickListener.setOnViewClickListener(holder.itemView, position, dateData);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //每次刷新获取数据总数时先将原数据清空
        dayDataList.clear();
        //获取itemcount思路：
        //1.列表要展示item总数=日期总数+展开每日诊断的总数
        //2.比如有4个天，每天诊断10次，
        //3.如果4天全部折叠，那么要展示item总数=4；
        //4.如果4天全部展开，那么要展示的item总数= 4 + 4 * 10 = 44，即天数和诊断数目总和。
        //5.首先基数size = 天数
        //首先给定基数size = 测试天数
        int size = dayNumList.size();
        //遍历天数列表
        for (int i = 0; i < dayNumList.size(); i++) {
            //将每天的实体放进集合里
            dayDataList.add(dayNumList.get(i));
            //判断一级目录展开状态，如果展开了，则每天数据在界面展示出来
            if (dayNumList.get(i).isExpand() && null != dayNumList.get(i).getItem()) {
                //根据4
                size += dayNumList.get(i).getItem().size();
                //每天数据获取
                dayDataList.addAll(dayNumList.get(i).getItem());
                //一级目录与二级目录数据绑定
                for (int j = 0; j < dayNumList.get(i).getItem().size(); j++) {
                    dayNumList.get(i).getItem().get(j).setParentName(dayNumList.get(i).getDate());
                }
            }
        }
        if (mOnUIChangeListener != null) {
            mOnUIChangeListener.getItems(dayDataList);
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (dayDataList.get(position) instanceof DiagnosisEvent) {
            //如果实现了父级实体类，表示该数据是日期份的实体
            return VIEW_PARENT;
        } else if (dayDataList.get(position) instanceof DiagnosisEventsItem) {
            //如果实现了子级实体类，表示该数据是日期对应诊断数据的实体
            return VIEW_CHILD;
        }
        return 0;
    }


    /**
     * 父级布局控件容器类
     */
    class ParentHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ParentHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_parent_content);
        }
    }

    /**
     * 子级布局控件容器类
     */
    class ChildHolder extends RecyclerView.ViewHolder {
        TextView textViewTime, textViewDiagIndex, textViewDiagLen, textViewNetState, textViewReason;
        ImageView imageView;

        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.tv_child_content_time);
            textViewDiagIndex = itemView.findViewById(R.id.tv_child_content_diag_index);
            textViewDiagLen = itemView.findViewById(R.id.tv_child_content_diag_len);
            textViewNetState = itemView.findViewById(R.id.tv_child_content_net_state);
            textViewReason = itemView.findViewById(R.id.tv_child_content_reason);
            imageView = itemView.findViewById(R.id.iv_child_view);
        }
    }

    /**
     * 获取显示数据
     *
     * @return
     */
    public List<Object> getObjects() {
        return dayDataList;
    }

    private String getText(DiagnosisEventsItem item) {
        //time len
        int time_len = getStringLen("time:", item.getTime());
        //diag_index len
        int diag_index_len = getStringLen("diag_index:", item.getDiag_index() + "");
        //diag_len len
        int diag_len_len = getStringLen("diag_len:", item.getDiag_len() + "");
        //net_state len
        int net_state_len = getStringLen("net_state:", item.getNet_state());
        //reason len
        int reason_len = getStringLen("reason:", item.getReason());
        //
        int max = getMax(time_len, diag_index_len, diag_len_len, net_state_len, reason_len);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("time:");
        stringBuffer.append(item.getTime());
        stringBuffer.append(getTabLenth(time_len, max));
        stringBuffer.append("diag_index:");
        stringBuffer.append(item.getDiag_index());
        stringBuffer.append(getTabLenth(diag_index_len, max));
        stringBuffer.append("diag_len:");
        stringBuffer.append(item.getDiag_len());
        stringBuffer.append("\n");
        stringBuffer.append("net_state:");
        stringBuffer.append(item.getNet_state());
        stringBuffer.append(getTabLenth(net_state_len, max));
        stringBuffer.append("reason:");
        stringBuffer.append(item.getReason());
        return stringBuffer.toString();
    }

    private String getTabLenth(int flag_len, int max) {
        int len = max - flag_len;
        StringBuilder stringBuffer = new StringBuilder();
        LogUtils.d(TAG, "len = " + len);
        for (int i = 0; i < len; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    private int getStringLen(String s, String str) {
        if (TextUtils.isEmpty(str)) {
            return s.length();
        }
        LogUtils.d(TAG, "getStringLen s = " + s + ",slen = " + s.length());
        LogUtils.d(TAG, "getStringLen str = " + str + ",strlen = " + str.length());
        return s.length() + str.length();
    }


    private int getMax(int time_len, int diag_index_len, int diag_len_len, int net_state_len, int reason_len) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(time_len);
        arrayList.add(diag_index_len);
        arrayList.add(diag_len_len);
        arrayList.add(net_state_len);
        arrayList.add(reason_len);
        int max = Collections.max(arrayList) + 3;
        LogUtils.d(TAG, "getMax time_len = " + time_len);
        LogUtils.d(TAG, "getMax diag_index_len = " + diag_index_len);
        LogUtils.d(TAG, "getMax diag_len_len = " + diag_len_len);
        LogUtils.d(TAG, "getMax net_state_len = " + net_state_len);
        LogUtils.d(TAG, "getMax reason_len = " + reason_len);
        LogUtils.d(TAG, "max = " + max);
        return max;
    }
}
