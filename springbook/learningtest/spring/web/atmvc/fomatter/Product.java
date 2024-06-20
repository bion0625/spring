package springbook.learningtest.spring.web.atmvc.fomatter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

public class Product {
    public Product() {}

    public Product(String name, BigDecimal price, Calendar birthday) {
        this.name = name;
        this.price = price;
        this.birthday = birthday;
    }

    String name;

    @NumberFormat(pattern = "$###,##0.00")
    BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Calendar birthday;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Calendar getBirthday() {
        return this.birthday;
    }

    public String getNow() {
        return LocalDate.now().toString();
    }
}
