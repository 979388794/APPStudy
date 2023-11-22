package com.example.DialogTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeScreen.R;

import java.util.List;

public class DialogRecyclerAdapter  extends RecyclerView.Adapter<DialogRecyclerAdapter.MyViewHolder>{

    private Context mContext;
    private List<Fruit> fruits;
    private OnItemClickListener mListener;

    public DialogRecyclerAdapter(Context mContext, List<Fruit> fruits, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.fruits = fruits;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Fruit fruit = fruits.get(position);
        holder.imageView.setImageResource(fruit.getImageID());
        holder.name.setText(fruit.getName());
        holder.price.setText(fruit.getPrice());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position,holder.imageView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fruit_image);
            name = itemView.findViewById(R.id.fruit_name);
            price = itemView.findViewById(R.id.fruit_price);
        }
    }
    // 外部接口回调监听
   public interface OnItemClickListener {
        void onClick(int pos,ImageView view);
    }
}