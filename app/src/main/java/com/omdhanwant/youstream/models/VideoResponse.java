package com.omdhanwant.youstream.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("results")
    private List<Video> results;


    public VideoResponse(List<Video> results) {
        this.results = results;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
