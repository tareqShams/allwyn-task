package com.api.task.automation.dataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDm extends MainDataModel {
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String idBook;
    private String toBeVerifiedAuthorId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getToBeVerifiedAuthorId() {
        return toBeVerifiedAuthorId;
    }

    public void setToBeVerifiedAuthorId(String toBeVerifiedAuthorId) {
        this.toBeVerifiedAuthorId = toBeVerifiedAuthorId;
    }

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

}
