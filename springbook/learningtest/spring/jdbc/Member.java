package springbook.learningtest.spring.jdbc;

public class Member {
    private String id;
    private String name;
    private double point;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
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
