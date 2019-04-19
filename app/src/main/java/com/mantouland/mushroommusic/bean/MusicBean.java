package com.mantouland.mushroommusic.bean;

import java.util.ArrayList;

/**
 * Created by asparaw on 2019/4/15.
 */
public class MusicBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLyricPath() {
        return lyricPath;
    }

    public void setLyricPath(String lyricPath) {
        this.lyricPath = lyricPath;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<String> getArtist() {
        return artist;
    }

    public void setArtist(ArrayList<String> artist) {
        this.artist = artist;
    }

    public ArrayList<String> getSinger() {
        return singer;
    }

    public void setSinger(ArrayList<String> singer) {
        this.singer = singer;
    }

    public ArrayList<String> getZhuanji() {
        return zhuanji;
    }

    public void setZhuanji(ArrayList<String> zhuanji) {
        this.zhuanji = zhuanji;
    }

    private String name;
    private String id;//hashcode
    private String path;
    private String lyricPath;
    private int time;
    private ArrayList<String> artist;
    private ArrayList<String> singer;
    private ArrayList<String> zhuanji;

}
