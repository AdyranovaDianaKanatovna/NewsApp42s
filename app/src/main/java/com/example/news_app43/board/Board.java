package com.example.news_app43.board;

public class Board {
    private String title;
    private String desc;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Board(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
}
