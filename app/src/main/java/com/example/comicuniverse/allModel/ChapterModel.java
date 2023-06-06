package com.example.comicuniverse.allModel;

public class ChapterModel {
//    +id_Chapter
//+id_Comic: FK
//+name_Chapter
//+date//
   private String id_Chapter;
   private String name_Chapter;
   public  String linkChapter;
   private String dayPost_Chapter;
   private int ViewChapter;

   public ChapterModel() {
    }

    public ChapterModel(String id_Chapter, String name_Chapter, String linkChapter, String dayPost_Chapter,int ViewChapter) {
        this.id_Chapter = id_Chapter;
        this.name_Chapter = name_Chapter;
        this.linkChapter = linkChapter;
        this.dayPost_Chapter = dayPost_Chapter;
        this.ViewChapter=ViewChapter;
    }

    public String getId_Chapter() {
        return id_Chapter;
    }

    public void setId_Chapter(String id_Chapter) {
        this.id_Chapter = id_Chapter;
    }

    public String getName_Chapter() {
        return name_Chapter;
    }

    public void setName_Chapter(String name_Chapter) {
        this.name_Chapter = name_Chapter;
    }

    public String getLinkChapter() {
        return linkChapter;
    }

    public void setLinkChapter(String linkChapter) {
        this.linkChapter = linkChapter;
    }

    public String getDayPost_Chapter() {
        return dayPost_Chapter;
    }

    public void setDayPost_Chapter(String dayPost_Chapter) {
        this.dayPost_Chapter = dayPost_Chapter;
    }

    public int getViewChapter() {
        return ViewChapter;
    }

    public void setViewChapter(int viewChapter) {
        ViewChapter = viewChapter;
    }
}
