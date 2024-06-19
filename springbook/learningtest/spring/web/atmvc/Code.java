package springbook.learningtest.spring.web.atmvc;

public enum Code {
    ADMIN(1), USER(2), GUEST(3);

    private final int value;
    Code(int value) {
        this.value = value;
    }
}
