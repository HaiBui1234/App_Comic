package com.example.comicuniverse.allModel;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {
    private String id_User;
    private String fullName_User;
    private String userName;
    private  String passWord_User;
    private  int code_User;
    private  String avarta_Usre;
    private boolean isActive;
    private ArrayList<FavoriteComic> listFavorite;

    public UserModel() {
    }

    public UserModel(String id_User, String fullName_User, String userName, String passWord_User, int code_User, String avarta_Usre, boolean isActive, ArrayList<FavoriteComic> listFavorite) {
        this.id_User = id_User;
        this.fullName_User = fullName_User;
        this.userName = userName;
        this.passWord_User = passWord_User;
        this.code_User = code_User;
        this.avarta_Usre = avarta_Usre;
        this.isActive = isActive;
        this.listFavorite = listFavorite;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getFullName_User() {
        return fullName_User;
    }

    public void setFullName_User(String fullName_User) {
        this.fullName_User = fullName_User;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord_User() {
        return passWord_User;
    }

    public void setPassWord_User(String passWord_User) {
        this.passWord_User = passWord_User;
    }

    public int getCode_User() {
        return code_User;
    }

    public void setCode_User(int code_User) {
        this.code_User = code_User;
    }

    public String getAvarta_Usre() {
        return avarta_Usre;
    }

    public void setAvarta_Usre(String avarta_Usre) {
        this.avarta_Usre = avarta_Usre;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ArrayList<FavoriteComic> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<FavoriteComic> listFavorite) {
        this.listFavorite = listFavorite;
    }
}
