package com.example.zhangziquan.sysu_smarthealth;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {
    private OnItemClickListener onItemClickListener;
    private Context context;
    private int layoutId;
    private List<T> data;

    public MyRecyclerViewAdapter(Context _context, int _layoutId, List _data) {
        context = _context;
        layoutId = _layoutId;
        data = _data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = MyViewHolder.get(context, parent, layoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        convert(holder, data.get(position)); // convert函数需要重写，下面会讲
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(holder.getAdapterPosition());
                    final int position = holder.getAdapterPosition();
                    final Collection food = (Collection) data.get(position);
                    data.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context.getApplicationContext(), food.getName()+"已删除", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
    public abstract void convert(MyViewHolder holder, T t);

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this.onItemClickListener = _onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 0;
        }
        else {
            return data.size();
        }
    }

    public T getItem(int position){
        return data.get(position);
    }

    public void refresh (List<T>_list){
        data = _list;
        notifyDataSetChanged();
    }
}
