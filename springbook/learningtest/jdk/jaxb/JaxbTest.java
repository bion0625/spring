package springbook.learningtest.jdk.jaxb;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import springbook.sqlservice.jaxb.SqlType;
import springbook.sqlservice.jaxb.Sqlmap;

public class JaxbTest {

    public static void main(String[] args) {
        JUnitCore.main(new String[] {
            "springbook.learningtest.jdk.jaxb.JaxbTest", 
            "springbook.learningtest.jdk.DynamicProxyTest", 
            "springbook.learningtest.junit.JUnitTest", 
            "springbook.learningtest.spring.factorybean.FactoryBeanTest",
            "springbook.learningtest.spring.pointcut.PointcutExpressionTest",
            "springbook.learningtest.template.CalcSumTest",
            "springbook.user.dao.UserDaoTest",
            "springbook.user.service.UserServiceTest",
            "springbook.user.service.UserTest"
        });
    }
    
    @Test
    public void readSqlmap() throws JAXBException, IOException {
        String contextPath = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(contextPath);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(
            getClass().getResourceAsStream("sqlmap.xml"));
        List<SqlType> sqlList = sqlmap.getSql();

        assertThat(sqlList.size(), is(3));
        assertThat(sqlList.get(0).getKey(), is("add"));
        assertThat(sqlList.get(0).getValue(), is("insert"));
        assertThat(sqlList.get(1).getKey(), is("get"));
        assertThat(sqlList.get(1).getValue(), is("select"));
        assertThat(sqlList.get(2).getKey(), is("delete"));
        assertThat(sqlList.get(2).getValue(), is("delete"));
    }
}
