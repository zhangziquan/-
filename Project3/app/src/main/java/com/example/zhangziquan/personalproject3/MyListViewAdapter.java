package com.example.zhangziquan.personalproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private List<Comment> list;
    private Context context;
    private int user_id;

    public MyListViewAdapter(Context _context, List<Comment> list, int userid) {
        this.context = _context;
        this.list = list;
        user_id = userid;
    }
    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i);
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        // 新声明一个View变量和ViewHoleder变量,ViewHolder类在下面定义。
        View convertView;
        final ViewHolder viewHolder;
        // 当view为空时才加载布局，否则，直接修改内容
        if (view == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            view = LayoutInflater.from(context).inflate(R.layout.item, null);
            convertView = view;
            viewHolder = new ViewHolder();
            viewHolder.user_name = (TextView) view.findViewById(R.id.User_name);
            viewHolder.user_avatar = (ImageView) view.findViewById(R.id.user_avatar);
            viewHolder.comment = (TextView) view.findViewById(R.id.user_comment);
            viewHolder.comment_time = (TextView) view.findViewById(R.id.comment_time);
            viewHolder.like = (ImageView) view.findViewById(R.id.like);
            viewHolder.like_num = (TextView) view.findViewById(R.id.like_num);
            convertView.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.user_name.setText(list.get(i).getUsername());
        viewHolder.user_avatar.setImageDrawable(list.get(i).getUserAvatar());
        viewHolder.comment.setText(list.get(i).getComment());
        viewHolder.comment_time.setText(list.get(i).getComment_time());
        viewHolder.like_num.setText(Integer.toString(list.get(i).getLike_num()));
        if(list.get(i).getUserlike().contains(user_id)){
            viewHolder.like.setImageResource(R.drawable.red);
        }else{
            viewHolder.like.setImageResource(R.drawable.white);
        }
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemLikeListener.onLikeClick(i)){
                    viewHolder.like.setImageResource(R.drawable.red);
                    Toast.makeText(context,"你的点赞使漩涡扩大",Toast.LENGTH_SHORT);
                }else{
                    viewHolder.like.setImageResource(R.drawable.white);
                }
            }
        });
        // 将这个处理好的view返回
        return convertView;
    }

    private class ViewHolder {
        public ImageView user_avatar;
        public TextView user_name;
        public TextView comment_time;
        public TextView comment;
        public TextView like_num;
        public ImageView like;
    }

    public interface onItemLikeListener {
        boolean onLikeClick(int i);
    }

    private onItemLikeListener mOnItemLikeListener;

    public void setOnItemLikeClickListener(onItemLikeListener mOnItemLikeListener) {
        this.mOnItemLikeListener = mOnItemLikeListener;
    }

    public void refresh (List<Comment>_list){
        list = _list;
        notifyDataSetChanged();
    }

    public void changelike(int id){

    }
}