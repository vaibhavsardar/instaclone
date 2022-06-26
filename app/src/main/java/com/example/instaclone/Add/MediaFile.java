package com.example.instaclone.Add;

public class MediaFile {

    private long id;
    private String buket;


    public MediaFile(long id, String buket) {
        this.id = id;
        this.buket =buket;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getBuket() {
        return buket;
    }

    public void setBuket(String buket) {
        this.buket = buket;
    }
}
