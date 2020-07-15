package com.omdhanwant.youstream.models;


import com.google.gson.annotations.SerializedName;


public class Video {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("size")
    private String size;
    @SerializedName("site")
    private String site;
    @SerializedName("key")
    private String key;

    public Video(String id, String name, String size, String site, String key) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.site = site;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
