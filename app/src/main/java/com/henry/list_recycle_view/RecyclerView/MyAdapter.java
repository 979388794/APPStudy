package com.henry.list_recycle_view.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.henry.basic.R;
import com.henry.list_recycle_view.listview.Fruit;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    OnItemClickListener mOnItemClickListener;
    Context mContext;
    List<Fruit> List;
    String Tag="xuejie";
    private int mSelectIndex = 0;

    public MyAdapter(Context context, List<Fruit> list) {
        this.mContext = context;
        this.List = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(Tag,"----------onCreateViewHolder----------");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(Tag,"---------onBindViewHolder-----------");
        holder.imageView.setImageResource(List.get(position).getImageID());
        holder.t1.setText(List.get(position).getName());
        holder.t2.setText(List.get(position).getPrice());
    }


    @Override
    public int getItemCount() {
        Log.d(Tag,"---------getItemCount-----------");
        return List.size();
    }


    public void setSelectIndex(int index) {
        mSelectIndex = index;
    }

    public int getSelectIndex() {
        return mSelectIndex;
    }



    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }




    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView t1;
        TextView t2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(getAdapterPosition()," update for 1");
                    notifyItemChanged(getAdapterPosition()," update for 2");
                }
            });
            imageView = itemView.findViewById(R.id.recycler_image);
            t1 = itemView.findViewById(R.id.recycler_t1);
            t2 = itemView.findViewById(R.id.recycler_t2);
        }
    }
}
