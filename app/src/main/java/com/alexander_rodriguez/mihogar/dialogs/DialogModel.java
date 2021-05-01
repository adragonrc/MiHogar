package com.alexander_rodriguez.mihogar.dialogs;

public class DialogModel {
    private String title;
    private String mensaje;

    public DialogModel(String title, String mensaje) {
        this.title = title;
        this.mensaje = mensaje;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
