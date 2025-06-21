package com.api.task.automation.dataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDataModel {


    private String testCaseTitle;
    private String testCaseId;
    private String expectedResponseCode;
    private String expectedResponseTitle;

    public String getExpectedResponseTitle() {
        return expectedResponseTitle;
    }

    public void setExpectedResponseTitle(String expectedResponseTitle) {
        this.expectedResponseTitle = expectedResponseTitle;
    }


    public String getExpectedResponseCode() {
        return expectedResponseCode;
    }

    public void setExpectedResponseCode(String expectedResponseCode) {
        this.expectedResponseCode = expectedResponseCode;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getTestCaseTitle() {
        return testCaseTitle;
    }

    public void setTestCaseTitle(String testCaseTitle) {
        this.testCaseTitle = testCaseTitle;
    }


}
