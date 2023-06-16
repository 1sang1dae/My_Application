package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class MyWishManager {
    private List<MyWish> wishlist;

    public MyWishManager() {
        wishlist = new ArrayList<>();
    }

    public void addMyWish(MyWish mywish) {
        wishlist.add(mywish);
    }

    public void removeMyWish(MyWish mywish) {
        wishlist.remove(mywish);
    }

    public List<MyWish> getWishlist() {
        return wishlist;
    }
}
