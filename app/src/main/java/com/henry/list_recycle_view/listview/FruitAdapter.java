package com.henry.list_recycle_view.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.henry.basic.R;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {
    public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
    }

    /**
     * 目前我们ListView的运行效率是很低的，因为在FruitAdapter的 getView()方法中，每次都将布局重 新加载了一遍，将快速滚动的时候， 这将会成为性能的瓶颈。
     */
   /*
    //每个子项被滚动到屏幕内的时候会被调用
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);//得到当前项的 Fruit 实例
        //为每一个子项加载设定的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fruit_item, parent, false);
        //分别获取 image view 和 textview 的实例
        ImageView fruitimage = view.findViewById(R.id.fruit_image);
        TextView fruitname = view.findViewById(R.id.fruit_name);
        TextView fruitprice = view.findViewById(R.id.fruit_price);
        // 设置要显示的图片和文字
        fruitimage.setImageResource(fruit.getImageID());
        fruitname.setText(fruit.getName());
        fruitprice.setText(fruit.getPrice());
        return view;
    }
    */

    // convertView 参数用于将之前加载好的布局进行缓存
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);//获取当前项的 Fruit 实例
        View view;
        //新增一个内部类 ViewHolder，用于对控件的实例进行缓存
        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        ViewHolder viewHolder;
        if (convertView == null) {
            //为每一个子项加载设定的布局
            view = LayoutInflater.from(getContext()).inflate(R.layout.fruit_item, parent, false);
            viewHolder = new ViewHolder();
            //分别获取 imageview 和 textview 的实例
            viewHolder.fruitimage = view.findViewById(R.id.fruit_image);
            viewHolder.fruitname = view.findViewById(R.id.fruit_name);
            viewHolder.fruitprice = view.findViewById(R.id.fruit_price);
            view.setTag(viewHolder);//将 viewHolder 存储在 view 中（即将控件的实例存储在其中）
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取 viewHolder
        }
        // 设置要显示的图片和文字
        viewHolder.fruitimage.setImageResource(fruit.getImageID());
        viewHolder.fruitname.setText(fruit.getName());
        viewHolder.fruitprice.setText(fruit.getPrice());
        return view;
    }

    private class ViewHolder {
        ImageView fruitimage;
        TextView fruitname;
        TextView fruitprice;
    }

}
