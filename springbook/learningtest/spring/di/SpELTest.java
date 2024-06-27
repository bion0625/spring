package springbook.learningtest.spring.di;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpELTest {
    @Test
    public void expressionParser() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression ex = parser.parseExpression("1+2");
        assertThat(ex.getValue().toString(), is("3"));
    }

    @Test
    public void expressionParserByRootObject() {
        User user1 = new User("gyumee", "박성철", "springno1", "test01@test.com", Level.BASIC, 1, 0);
        User user2 = new User("leegw700", "이길원", "springno2", "test02@test.com", Level.BASIC, 1, 0);
        User user3 = new User("bumjin", "박범진", "springno3", "test03@test.com", Level.BASIC, 1, 0);

        User[] users = {user1, user2, user3};

        SpelExpressionParser parser = new SpelExpressionParser();
        Expression nameEx = parser.parseExpression("name");
        List<String> nameList = new ArrayList<>();
        for (User user : users) {
            nameList.add(nameEx.getValue(user).toString());
        }
        for (String name : nameList) {
            assertThat(name, is(users[nameList.indexOf(name)].getName()));
        }
    }
}
