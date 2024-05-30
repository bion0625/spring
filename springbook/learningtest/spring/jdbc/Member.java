package springbook.learningtest.spring.jdbc;

public class Member {

    public Member() {}

    private String id;
    private String name;
    private double point;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getPoint() {
        return this.point;
    }

    public Member(String id, String name, double point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }
}
