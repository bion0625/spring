package springbook.learningtest.spring.ioc.xml.property.bean;

public class StringPrinter implements Printer {
    private StringBuffer buffer = new StringBuffer();

    // Printer 인터페이스의 메소드. 내장 버퍼에 메시지를 추가해준다.
    @Override
    public void print(String message) {
        this.buffer.append(message);
    }

    // 내장 버퍼에 추가해둔 메시지를 스트링으로 가져온다.
    @Override
    public String toString() {
        return this.buffer.toString();
    }
}
