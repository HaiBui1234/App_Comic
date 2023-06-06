package com.example.comicuniverse.allModel;

import java.util.ArrayList;

public class ListComic {
    private int type;
    private String title;
    private ArrayList<ComicModel> data;
    private  ArrayList<CategoryModel> dataCate;

    public ListComic(int type, String title, ArrayList<ComicModel> data, ArrayList<CategoryModel> dataCate) {
        this.type = type;
        this.title = title;
        this.data = data;
        this.dataCate = dataCate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ComicModel> getData() {
        return data;
    }

    public void setData(ArrayList<ComicModel> data) {
        this.data = data;
    }

    public ArrayList<CategoryModel> getDataCate() {
        return dataCate;
    }

    public void setDataCate(ArrayList<CategoryModel> dataCate) {
        this.dataCate = dataCate;
    }
}
