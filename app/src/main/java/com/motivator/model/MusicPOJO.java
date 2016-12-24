package com.motivator.model;

/**
 * Created by sunil on 11-11-2016.
 */
public class MusicPOJO {
    String music_id;
    String music_title;
    String music_description;
    String music_name;
    String music_date;
    String music_category;
    String music_file_path;

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public String getMusic_title() {
        return music_title;
    }

    public void setMusic_title(String music_title) {
        this.music_title = music_title;
    }

    public String getMusic_description() {
        return music_description;
    }

    public void setMusic_description(String music_description) {
        this.music_description = music_description;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getMusic_date() {
        return music_date;
    }

    public void setMusic_date(String music_date) {
        this.music_date = music_date;
    }

    public String getMusic_category() {
        return music_category;
    }

    public void setMusic_category(String music_category) {
        this.music_category = music_category;
    }

    public String getMusic_file_path() {
        return music_file_path;
    }

    public void setMusic_file_path(String music_file_path) {
        this.music_file_path = music_file_path;
    }

    @Override
    public String toString() {
        return "MusicPOJO{" +
                "music_id='" + music_id + '\'' +
                ", music_title='" + music_title + '\'' +
                ", music_description='" + music_description + '\'' +
                ", music_name='" + music_name + '\'' +
                ", music_date='" + music_date + '\'' +
                ", music_category='" + music_category + '\'' +
                ", music_file_path='" + music_file_path + '\'' +
                '}';
    }
}
