package springbook.learningtest.spring.web.atmvc;

public class User {
    int id;
    String name;
    Code userType;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setUserType(Code userType) {
        this.userType = userType;
    }
    public Code getUserType() {
        return this.userType;
    }
}
