package com.example.comicuniverse.allModel;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class FavoriteComic implements Serializable {
    private String id_Comic;
    private String id_Category;
    private String title;
    private String date;
    private String img_comic;
    private String name_Author;
    private String name_Comic;
    private int ViewComic;
    private ArrayList<ChapterModel> chapterModels;
    private String desScription;
    private boolean historyComic;
    private boolean favouriteComic;

    public FavoriteComic() {
    }

    public FavoriteComic(String id_Comic, String id_Category, String title, String date, String img_comic, String name_Author, String name_Comic, int viewComic,ArrayList<ChapterModel> chapterModels,String desScription, boolean historyComic, boolean favouriteComic) {
        this.id_Comic = id_Comic;
        this.id_Category = id_Category;
        this.title = title;
        this.date = date;
        this.img_comic = img_comic;
        this.name_Author = name_Author;
        this.name_Comic = name_Comic;
        this.ViewComic = viewComic;
        this.chapterModels=chapterModels;
        this.desScription=desScription;
        this.historyComic = historyComic;
        this.favouriteComic = favouriteComic;
    }

    public String getId_Comic() {
        return id_Comic;
    }

    public void setId_Comic(String id_Comic) {
        this.id_Comic = id_Comic;
    }

    public String getId_Category() {
        return id_Category;
    }

    public void setId_Category(String id_Category) {
        this.id_Category = id_Category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg_comic() {
        return img_comic;
    }

    public void setImg_comic(String img_comic) {
        this.img_comic = img_comic;
    }

    public String getName_Author() {
        return name_Author;
    }

    public void setName_Author(String name_Author) {
        this.name_Author = name_Author;
    }

    public String getName_Comic() {
        return name_Comic;
    }

    public void setName_Comic(String name_Comic) {
        this.name_Comic = name_Comic;
    }

    public boolean isHistoryComic() {
        return historyComic;
    }

    public void setHistoryComic(boolean historyComic) {
        this.historyComic = historyComic;
    }

    public boolean isFavouriteComic() {
        return favouriteComic;
    }

    public int getViewComic() {
        return ViewComic;
    }

    public void setViewComic(int viewComic) {
        ViewComic = viewComic;
    }

    public ArrayList<ChapterModel> getChapterModels() {
        return chapterModels;
    }

    public void setChapterModels(ArrayList<ChapterModel> chapterModels) {
        this.chapterModels = chapterModels;
    }

    public void setFavouriteComic(boolean favouriteComic) {
        this.favouriteComic = favouriteComic;
    }

    public String getDesScription() {
        return desScription;
    }

    public void setDesScription(String desScription) {
        this.desScription = desScription;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name_Comic;
    }
}
