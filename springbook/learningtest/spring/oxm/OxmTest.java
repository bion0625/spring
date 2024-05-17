package springbook.learningtest.spring.oxm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller; // JAXB API 등에서 사용하는 언마샬러와 클래스 이름이 같으므로 임포트할 때 주의해야 한다.
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.sqlservice.jaxb.SqlType;
import springbook.sqlservice.jaxb.Sqlmap;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration // 클래스 이름 + '-context.xml' 파일을 사용하는 애플리케이션 컨텍스트로 만들어서 테스트가 사용할 수 있게 한다.
public class OxmTest {

    // 스프링 테스트가 테스트용 어플리케이션 컨텍스트에서 Unmarshaller 인터페이스 타입의 빈을 찾아서 테스트가 시작되기 전에 이 변수에 넣어준다.
    @Autowired Unmarshaller unmarshaller;

    @Test
    public void unmarshallerSqlMap() throws XmlMappingException, IOException {
        Source xmlSource = new StreamSource(
                getClass().getResourceAsStream("sqlmap.xml")); // InputStream을 이용하는 Source 타입의 StreamSource를 만든다.

        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource); // 어떤 OXM 기술이든 언마샬은 이 한 줄이면 끝이다.

        List<SqlType> sqlList = sqlmap.getSql();
        assertThat(sqlList.size(), is(3));

        // JaxbTest와 동일하게 sqlmap.xml 파일의 내용을 정확히 가져왔는지 검사한다.
        assertThat(sqlList.get(0).getKey(), is("add"));
        assertThat(sqlList.get(1).getKey(), is("get"));
        assertThat(sqlList.get(2).getKey(), is("delete"));
    }
}
