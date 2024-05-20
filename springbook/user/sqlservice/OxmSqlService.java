package springbook.user.sqlservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OxmSqlService implements SqlService {
    // SqlService의 실제 구현 부분을 위임할 대상인 BaseSqlService를 인스턴스 변수로 정의해둔다.
    private final BaseSqlService baseSqlService = new BaseSqlService();
    private final OxmSqlReader oxmSqlReader = new OxmSqlReader();

    /*
    * oxmSqlReader와 달리 단지 디폴트 오브젝트로 만들어진 프로퍼티다.
    * 따라서 필요에 따라 DI를 통해 교체 가능하다.
    * */
    private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    /*
    * OmxSqlService의 공개된 프로퍼티를 통해 DI 받은 것을 그대로 멤버 클래스의 오브젝트에 전달한다.
    * 이 setter들은 단일 빈 설정구조를 위한 창구 역할을 할 뿐이다.
    * */
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    // 이름과 타입을 모두 변경한다. 큰 변화긴 하지만 그만큼 기능을 확장하고 유연성을 얻는 것이니 과감하게 변경한다.
    public void setSqlmap(Resource sqlmap) {
        this.oxmSqlReader.setSqlmap(sqlmap);
    }

    // SqlService 인터페이스에 대한 구현 코드는 BaseSqlService와 같다.
    @PostConstruct
    public void loadSql() {
        // OxmSqlService의 프로퍼티를 통해서 초기화된 SqlReader와 SqlRegistry를 실제 작업을 위임할 대상인 baseSqlService에 주입한다.
        this.baseSqlService.setSqlReader(this.oxmSqlReader);
        this.baseSqlService.setSqlRegistry(this.sqlRegistry);

        // SQL을 등록하는 초기화 작업을 baseSqlService에 위임한다.
        this.baseSqlService.loadSql();
    }

    @Override
    public String getSql(String key) throws SqlRetrivalFailureException {
        // SQL을 찾아오는 작업도 baseSqlService에 위임한다.
        return this.baseSqlService.getSql(key);
    }

    private class OxmSqlReader implements SqlReader {
        private Unmarshaller unmarshaller;

        // 디폴트 파일은 기존과 같지만 이제는 Resource 구현 클래스인 ClassPathResource를 이용한다.
        private Resource sqlmap = new ClassPathResource("sqlmap.xml", UserDao.class);

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }
        public void setSqlmap(Resource sqlmap) {
            this.sqlmap = sqlmap;
        }

        @Override
        public void read(SqlRegistry sqlRegistry) {
            try {
                Source source = new StreamSource(sqlmap.getInputStream()); // 리소스의 종류에 상관없이 스트림으로 가져올 수 있다.
                // OxmSqlService를 통해 전달받은 OXM 인터페이스 구현 오브젝트를 가지고 언마샬링 작업 수행
                Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);
                for (SqlType sql : sqlmap.getSql()) {
                    sqlRegistry.registerSql(sql.getKey(), sql.getValue());
                }
            } catch (IOException e) {
                /*
                * 언마샬 작업 중 IO 에러가 났다면 설정을 통해 제공받은 XML 파일 이름이나 정보가 잘못됐을 가능성이 제일 높다.
                * 이런 경우에 가장 적합한 런타임 예외 중 하나만 IllegalArgumentException으로 포장해서 던진다.
                * */
                throw new IllegalArgumentException(String.format("%s을 가져올 수 없습니다", this.sqlmap.getFilename()), e);
            }
        }
    }
}
