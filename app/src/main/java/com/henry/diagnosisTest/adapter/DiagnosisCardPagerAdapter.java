package com.henry.diagnosisTest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;


import com.henry.basic.R;
import com.henry.diagnosisTest.model.DiagnosisInfoList;

import java.util.ArrayList;
import java.util.List;


public class DiagnosisCardPagerAdapter extends PagerAdapter {
    public static int MAX_ELEVATION_FACTOR = 8;
    private List<CardView> mViews;
    private List<DiagnosisInfoList> mData;
    private float mBaseElevation;
    private DiagnosisCardPagerClickListener clickListener;

    public DiagnosisCardPagerAdapter(List<DiagnosisInfoList> mData) {
        this.mData = mData;
        mViews = new ArrayList<>();
    }

    public void setClickListener(DiagnosisCardPagerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewHolder viewHolder = (ViewHolder) object;
        return viewHolder.mItemView == view;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        ViewHolder<DiagnosisInfoList> viewHolder = (ViewHolder<DiagnosisInfoList>) object;
        DiagnosisInfoList item = viewHolder.mItem;
        // 当前内存中页面数据是否还存在于刷新后的数据集合中
        int newIndex = mData.indexOf(item);
        int itemPosition = newIndex == -1 ? POSITION_NONE : newIndex;
        int oldPosition = viewHolder.mPosition;
        // 数据发生了位置改变
        if (itemPosition >= 0 && oldPosition != itemPosition) {
            // 更新新的索引位置
            viewHolder.mPosition = itemPosition;
        }
        return itemPosition;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_diagnosis_viewpager, container, false);
        container.addView(view);
        DiagnosisInfoList item = mData.get(position);
        bind(item, view);
        CardView cardView = view.findViewById(R.id.cardView);
        LinearLayout ll_item = view.findViewById(R.id.ll_item);
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        ImageView ivWarning = view.findViewById(R.id.ivWarning);
        //Button btEnter = view.findViewById(R.id.btEnter);

        titleTextView.setText(mData.get(position).getDiagnosisName());
        ivWarning.setImageResource(
                mData.get(position).getStatusCode() != 0 ? R.mipmap.icon_warning : R.mipmap.icon_success);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        if (clickListener != null) {
//            btEnter.setOnClickListener((V) -> {
//                clickListener.click(position);
//            });
            ll_item.setOnClickListener((V)->{
                clickListener.click(position);
            });
        }
        mViews.add(position, cardView);
        return new ViewHolder<DiagnosisInfoList>(view, item, position);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewHolder viewHolder = (ViewHolder) object;
        container.removeView(viewHolder.mItemView);
        mViews.set(position, null);
    }

    private void bind(DiagnosisInfoList item, View view) {
    }

    public void setData(ArrayList<DiagnosisInfoList> data) {
        mData.clear();
        mData.addAll(data);
    }


    public interface DiagnosisCardPagerClickListener {
        public void click(int position);
    }

    // 封装了itemView以及item数据，建立映射关系
    public static class ViewHolder<DiagnosisInfoList> {
        private View mItemView;
        private DiagnosisInfoList mItem;
        private int mPosition;

        ViewHolder(View itemView, DiagnosisInfoList item, int position) {
            mItemView = itemView;
            mItem = item;
            mPosition = position;
        }

        public DiagnosisInfoList getmItem() {
            return mItem;
        }

        public int getmPosition() {
            return mPosition;
        }

        public View getmItemView() {
            return mItemView;
        }

        public void setmItem(DiagnosisInfoList mItem) {
            this.mItem = mItem;
        }

        public void setmItemView(View mItemView) {
            this.mItemView = mItemView;
        }

        public void setmPosition(int mPosition) {
            this.mPosition = mPosition;
        }

    }
}
