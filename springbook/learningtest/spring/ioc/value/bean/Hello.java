package springbook.learningtest.spring.ioc.value.bean;


import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Hello {
    private String name;
    private List<String> names;
    private Map<String, Integer> ages;

    private Properties setting;

    private List<Object> beans;

    private Boolean flag;

    private double rate;

    private int[] intarr;
    Printer printer;

    // 프로퍼티로 DI 받은 이름을 이용해 간단한 인사문구 만들기
    public String sayHello() {
        return "Hello " + name;
    }

    public void print() {
        this.printer.print(sayHello());
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setAges(Map<String, Integer> ages) {
        this.ages = ages;
    }

    public Map<String, Integer> getAges() {
        return this.ages;
    }

    public void setSetting(Properties setting) {
        this.setting = setting;
    }

    public Properties getSetting() {
        return this.setting;
    }

    public void setBeans(List<Object> beans) {
        this.beans = beans;
    }

    public List<Object> getBeans() {
        return this.beans;
    }

    public void setFlag (Boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return this.rate;
    }

    public void setIntarr(int[] intarr) {
        this.intarr = intarr;
    }

    public int[] getIntarr() {
        return this.intarr;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
