package springbook.learningtest.spring.web.ajax;

public class Result {

    public Result() {}
    public Result(boolean duplicated, String availableId) {
        this.duplicated = duplicated;
        this.availableId = availableId;
    }
    boolean duplicated;
    String availableId;

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }

    public Boolean getDuplicated() {
        return this.duplicated;
    }

    public void setAvailableId(String availableId) {
        this.availableId = availableId;
    }

    public String getAvailableId() {
        return this.availableId;
    }
}
