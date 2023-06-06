package com.example.comicuniverse.AllInterface;

import androidx.fragment.app.Fragment;

import com.example.comicuniverse.allModel.FavoriteComic;

public interface InterFaceFavorite {
    void  addFavorite(FavoriteComic favoriteComic,String id);
    void  adDeleteFavorite(String id);

}
