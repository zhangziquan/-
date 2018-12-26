package com.example.zhangziquan.personalproject5;

import android.graphics.Bitmap;

public class Video {

    private Boolean status;
    private Data data;

    public static class Data{
        private Integer aid;
        private String title;
        private String content;
        private String video_review;
        private String create;
        private String duration;
        private String cover;
        private Integer play;
        private String rec;
        private Integer count;
        private Bitmap coverimage;
        private Bitmap[] spiritimage;
        private Integer[] index;


        public Data(Integer aid, String title, String content, String video_review, String create,
                    String duration, String cover, Integer play, String rec, Integer count){
            this.aid = aid;
            this.title = title;
            this.content = content;
            this.video_review = video_review;
            this.create = create;
            this.duration = duration;
            this.cover = cover;
            this.play = play;
            this.rec = rec;
            this.count = count;
            spiritimage = null;
            index = null;
            coverimage = null;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setVideo_review(String video_review) {
            this.video_review = video_review;
        }

        public void setCreate(String create) {
            this.create = create;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setPlay(Integer play) {
            this.play = play;
        }

        public void setRec(String rec) {
            this.rec = rec;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public void setSpiritimage(Bitmap[] spiritimage) {
            this.spiritimage = spiritimage;
        }

        public void setIndex(Integer[] index) {
            this.index = index;
        }

        public void setCoverimage(Bitmap coverimage) {
            this.coverimage = coverimage;
        }

        public Integer getAid() {
            return aid;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getVideo_review() {
            return video_review;
        }

        public String getCreate() {
            return create;
        }

        public String getDuration() {
            return duration;
        }

        public String getCover() {
            return cover;
        }

        public Integer getPlay() {
            return play;
        }

        public String getRec() {
            return rec;
        }

        public Integer getCount() {
            return count;
        }

        public Bitmap[] getSpiritimage() {
            return spiritimage;
        }

        public Integer[] getIndex() {
            return index;
        }

        public Bitmap getCoverimage() {
            return coverimage;
        }
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
