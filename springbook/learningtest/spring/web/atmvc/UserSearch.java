package springbook.learningtest.spring.web.atmvc;

public class UserSearch {
    private int id;
    private String name;
    private int level;
    private String email;

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
    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
