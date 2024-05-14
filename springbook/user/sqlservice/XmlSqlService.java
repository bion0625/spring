package springbook.user.sqlservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService {
    // 읽어온 SQL을 저장해둘 맵
    private Map<String, String> sqlMap = new HashMap<>();

    private String sqlFile;

    public void setSqlFile(String sqlFile) {
        this.sqlFile = sqlFile;
    }

    @PostConstruct // loadSql 메소드를 빈의 초기화 메소드로 지정한다.
    public void loadSql() {
        // JAXB API를 이용해 XML문서를 오브젝트 트리로 읽어온다.
        String contextPath = Sqlmap.class.getPackage().getName();
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // UserDao와 같은 클래스패스의 sqlmap.xml 파일을 변환한다.
            InputStream is = UserDao.class.getResourceAsStream(this.sqlFile); // 프로퍼티 설정을 통해 제공받은 파일 이름을 사용한다.
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            // 읽어온 SQL을 맵으로 저장해둔다.
            for (SqlType sql : sqlmap.getSql()) {
                sqlMap.put(sql.getKey(), sql.getValue());
            }
        } catch (JAXBException e) {
            // JAXBException은 복구 불가능한 예외다. 불필요한 throws를 피하도록 런타임 예외로 포장해서 던진다.
            throw new RuntimeException(e);
        }
    }

    public XmlSqlService() {
    }

    @Override
    public String getSql(String key) throws SqlRetrivalFailureException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrivalFailureException(String.format("%s를 이용해서 SQL을 찾을 수 없습니다.", key));
        }
        else
            return sql;
    }
}
