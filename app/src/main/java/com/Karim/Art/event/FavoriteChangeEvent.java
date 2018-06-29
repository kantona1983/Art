package com.Karim.Art.event;

/**
 * Created by HP-HP on 16-10-2016.
 */
public class FavoriteChangeEvent {

    public final boolean isFavorite;

    public FavoriteChangeEvent(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavoriteChanged() {
        return isFavorite;
    }

}
