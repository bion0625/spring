package springbook.learningtest.spring.web.atmvc.JSR303;

import org.springframework.format.annotation.NumberFormat;
import springbook.user.domain.Level;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class JSR303User {
    String id;

    @Size(min = 2, max = 10)
    String name;
    String password;
    String email;

    Level level;
    int login;
    int recommend;

    public JSR303User(String id, String name, String password, String email, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public JSR303User() {} // 자바빈의 규약을 따르는 클래스에 생성자를 명시적으로 추가했을 때는 파라미터가 없는 디폴트 생성자도 함께 정의해주는 것을 잊지 말자.

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return this.login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return this.recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalStateException(String.format("%s은 업그레이드가 불가능합니다.", this.level.name()));
        }
        else {
            this.level = nextLevel;
        }
    }

    @Override
    public String toString() {
        return this.id;
    }

    private boolean autoLogin;

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public boolean getAutoLogin() {
        return this.autoLogin;
    }

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @NumberFormat(pattern = "###,##0")
    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    private Set<String> interests;

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    public Set<String> getInterests() {
        return this.interests;
    }
}
