package com.api.task.automation.dataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDm extends MainDataModel {
    private String id;
    private String title;
    private String description;
    private String pageCount;
    private String publishDate;

    public String getToBeVerifiedBookId() {
        return toBeVerifiedBookId;
    }

    public void setToBeVerifiedBookId(String toBeVerifiedBookId) {
        this.toBeVerifiedBookId = toBeVerifiedBookId;
    }

    private String toBeVerifiedBookId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    private String excerpt;

}
