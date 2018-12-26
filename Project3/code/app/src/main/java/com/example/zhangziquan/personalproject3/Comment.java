package com.example.zhangziquan.personalproject3;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private Drawable UserAvatar;
    private String Username;
    private String comment_time;
    private String comment;
    private List<Integer> userlike;
    private int like_num;
    private int _id;

    public Comment(int id, Drawable Avatar, String name, String time, int likes, String new_comment, List list) {
        _id =id;
        UserAvatar = Avatar;
        Username = name;
        comment_time = time;
        userlike = new ArrayList<>();
        comment = new_comment;
        like_num = likes;
        userlike = list;
    }

    public void like(String username){

    }

    public String getUsername(){
        return Username;
    }

    public String getComment_time(){
        return comment_time;
    }

    public int getLike_num() {
        return like_num;
    }

    public Drawable getUserAvatar() {
        return UserAvatar;
    }

    public String getComment() {
        return comment;
    }

    public int get_id() {
        return _id;
    }

    public boolean likeordislike(int _id){
        if(userlike.contains(_id)){
            like_num --;
            for(int i =0;i<userlike.size();i++){
                if(userlike.get(i)==_id){
                    userlike.remove(i);
                }
            }
            return false;
        }else{
            like_num ++;
            userlike.add(_id);
            return true;
        }
    }

    public List<Integer> getUserlike() {
        return userlike;
    }
}
