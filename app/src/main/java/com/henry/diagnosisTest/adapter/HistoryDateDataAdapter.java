package com.henry.diagnosisTest.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.henry.basic.R;
import com.henry.diagnosisTest.listener.OnItemViewClickListener;
import com.henry.diagnosisTest.listener.OnUIChangeListener;
import com.henry.diagnosisTest.model.DiagnosisHistoryCatalogue;
import com.henry.diagnosisTest.model.DiagnosisHistoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryDateDataAdapter extends RecyclerView.Adapter {
    private final static int VIEW_PARENT = R.layout.item_parent;//父级布局
    private final static int VIEW_CHILD = R.layout.item_child_history;//子级布局
    private final String TAG = getClass().getSimpleName();

    //定义一个Object类型的集合来存放混合了day和item的数据实体
    private ArrayList<Object> dayDataList = new ArrayList<>();
    //day num 列表数据,其中days最大值7
    private ArrayList<DiagnosisHistoryCatalogue> dayNumList;
    private OnItemViewClickListener onItemViewClickListener;
    private OnUIChangeListener onUIChangeListener;

    public HistoryDateDataAdapter(ArrayList<DiagnosisHistoryCatalogue> item) {
        this.dayNumList = item;
    }

    public void setListData(ArrayList<DiagnosisHistoryCatalogue> item) {
        this.dayNumList = item;
    }

    ;

    public void setOnUIChangeListener(OnUIChangeListener onUIChangeListener) {
        this.onUIChangeListener = onUIChangeListener;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof ParentHolder) {
            ParentHolder parentHolder = (ParentHolder) holder;
            DiagnosisHistoryCatalogue historyDateData = (DiagnosisHistoryCatalogue) dayDataList.get(position);
            parentHolder.textView.setText(convertDay(historyDateData.getDay()));
            //测试日期item点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //更新展开折叠状态，如果当前是折叠，则变更为展开，如果当前是展开，则变成折叠
                    Log.d(TAG, "onClick: His parentHolder position " + position);
                    ((DiagnosisHistoryCatalogue) dayDataList.get(position)).setExpand(!((DiagnosisHistoryCatalogue) dayDataList.get(position)).isExpand());
                    //刷新列表UI
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof ChildHolder) {
            ChildHolder childHolder = (ChildHolder) holder;
            final DiagnosisHistoryItem dateData = (DiagnosisHistoryItem) dayDataList.get(position);
            childHolder.textView.setText(convertTime(dateData.getTime()));
            String status = dateData.getStatus();
            int status_int = Integer.parseInt(status);
            Log.d(TAG, "status_int = " + status_int + ",,status = " + status);
            if (status_int == 0) {
                //childHolder.imageView.setBackgroundResource(R.mipmap.icon_success);
                childHolder.imageView.setImageResource(R.mipmap.icon_success);
            } else {
                //childHolder.imageView.setBackgroundResource(R.mipmap.icon_warning);
                childHolder.imageView.setImageResource(R.mipmap.icon_warning);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemViewClickListener != null) {
                        onItemViewClickListener.setOnViewClickListener(holder.itemView, position, dateData);
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
            if (dayNumList.get(i).isExpand()) {
                //根据4
                size += dayNumList.get(i).getItem().size();
                //每天数据获取
                dayDataList.addAll(dayNumList.get(i).getItem());
                //一级目录与二级目录数据绑定
                for (int j = 0; j < dayNumList.get(i).getItem().size(); j++) {
                    dayNumList.get(i).getItem().get(j).setParentName(dayNumList.get(i).getDay());
                }
            }
        }
        if (onUIChangeListener != null) {
            onUIChangeListener.getItems(dayDataList);
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (dayDataList.get(position) instanceof DiagnosisHistoryCatalogue) {
            //如果实现了父级实体类，表示该数据是日期份的实体
            return VIEW_PARENT;
        } else if (dayDataList.get(position) instanceof DiagnosisHistoryItem) {
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
        TextView textView;
        ImageView imageView;

        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_child_content);
            imageView = itemView.findViewById(R.id.iv_child_view_his);
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
}
