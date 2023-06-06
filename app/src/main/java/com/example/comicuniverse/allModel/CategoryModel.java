package com.example.comicuniverse.allModel;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id_Catagory;
    private String name_Catagory;
    private String image_Category;

    public CategoryModel() {
    }

    public CategoryModel(String id_Catagory, String name_Catagory,String image_Category) {
        this.id_Catagory = id_Catagory;
        this.name_Catagory = name_Catagory;
        this.image_Category=image_Category;
    }

    public String getId_Catagory() {
        return id_Catagory;
    }

    public void setId_Catagory(String id_Catagory) {
        this.id_Catagory = id_Catagory;
    }

    public String getName_Catagory() {
        return name_Catagory;
    }

    public void setName_Catagory(String name_Catagory) {
        this.name_Catagory = name_Catagory;
    }

    public String getImage_Category() {
        return image_Category;
    }

    public void setImage_Category(String image_Category) {
        this.image_Category = image_Category;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name_Catagory;
    }
}
