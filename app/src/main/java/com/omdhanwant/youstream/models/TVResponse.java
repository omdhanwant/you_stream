package com.omdhanwant.youstream.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<TV> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public TVResponse(int page, List<TV> results, int totalResults, int totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TV> getResults() {
        return results;
    }

    public void setResults(List<TV> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
