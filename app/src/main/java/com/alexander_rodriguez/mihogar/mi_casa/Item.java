package com.alexander_rodriguez.mihogar.mi_casa;

import android.graphics.drawable.Drawable;

public class Item {
    private String idItem;
    private Drawable image;
    private String title;
    private String descripcion;
    public Item(String idItem, Drawable image, String title, String descripcion) {
        this.image = image;
        this.title = title;
        this.descripcion = descripcion;
        this.idItem = idItem;
    }

    public Drawable getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIdItem() {
        return idItem;
    }
}
